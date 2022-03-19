package infinitybank.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 *
 * @param <Entity> the generic type
 */
public class DAO <Entity> {
	
	/** The emf. */
	private static EntityManagerFactory emf = null;
	
	/** The em. */
	private EntityManager em = null;
	
	/** The entity. */
	private Entity entity = null;
	
	/** The class entity. */
	private Class<Entity> classEntity = null;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("jpa");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	{
		this.em = emf.createEntityManager();
	}
	
	/**
	 * Instantiates a new dao.
	 */
	public DAO() {
		System.out.println("dao sem parametros foi chamado");
		this.handleError();
	}
	
	/**
	 * Instantiates a new dao.
	 *
	 * @param classEntity the class entity
	 */
	public DAO(Class<Entity> classEntity) {
		this.classEntity = classEntity;
		System.out.println("dao com parametros foi chamado");
	}
	
	/**
	 * Open transaction.
	 *
	 * @return the dao
	 */
	private DAO<Entity> openTransaction() {
		this.em.getTransaction().begin();
		return this;
	}	
	
	/**
	 * Close transaction.
	 *
	 * @return the dao
	 */
	private DAO<Entity> closeTransaction() {
		this.em.getTransaction().commit();
		return this;
	}
	
	/**
	 * Merge.
	 *
	 * @param entity the entity
	 * @return the dao
	 */
	private DAO<Entity> merge(Entity entity){
		this.em.detach(entity);
		this.em.merge(entity);
		this.entity = entity;
		return this;
	}
	
	/**
	 * Removes the.
	 *
	 * @param entity the entity
	 * @return the dao
	 */
	private DAO<Entity> remove(Entity entity){
		this.em.remove(entity);
		return this;
	}
	
	/**
	 * Include.
	 *
	 * @param entity the entity
	 * @return the dao
	 */
	private DAO<Entity> include(Entity entity) {
		this.em.persist(entity);
		this.entity = entity;
		return this;
	}
	
	/**
	 *  CRUD CREATE *.
	 *
	 * @param entity the entity
	 * @return the dao
	 */
	public DAO<Entity> save(Entity entity) {
		this.openTransaction()
		.include(entity)
		.closeTransaction();
		return this;
	}
	
	/**
	 *  CRUD READ *.
	 *
	 * @param id the id
	 * @return the by id
	 */
	public Entity getById(Object id) {
		return this.em.find(this.classEntity, id);
	}
	
	/**
	 * Gets the all.
	 *
	 * @param limit the limit
	 * @param offset the offset
	 * @return the all
	 */
	public List<Entity> getAll(int limit, int offset) {
		if(classEntity == null) {
			throw new UnsupportedOperationException("Classe nula");
		}
		
		String jpql = "select e from "+classEntity.getName()+" e";
		TypedQuery<Entity> query = this.em.createQuery(jpql, classEntity);
		query.setMaxResults(limit);
		query.setFirstResult(offset);
		return query.getResultList();
	}
	
	/**
	 *  CRUD UPDATE *.
	 *
	 * @param id the id
	 * @param entity the entity
	 * @return the dao
	 */
	public DAO<Entity> update(Integer id, Entity entity) {
		if(classEntity == null) {
			throw new UnsupportedOperationException("Classe nula");
		}
		Entity hasEntity = this.getById(id);
		if(hasEntity == null) {
			throw new RuntimeException("Entitidade não encontrada.");
		}
		
		this.openTransaction()
		.merge(entity)
		.closeTransaction();
		return this;
	}
	
	/**
	 *  CRUD DELETE *.
	 *
	 * @param id the id
	 * @return the dao
	 */
	public DAO<Entity> delete(Integer id) {
		if(classEntity == null) {
			throw new UnsupportedOperationException("Classe nula");
		}
		Entity hasEntity = this.getById(id);
		if(hasEntity == null) {
			throw new RuntimeException("Entitidade não encontrada.");
		}
		
		this.openTransaction()
		.remove(hasEntity)
		.closeTransaction();
		return this;
	}
	
	/**
	 * Close.
	 *
	 * @return the entity
	 */
	public Entity close() {
		this.em.close();
		return this.entity;
	}
	
	/**
	 * Handle error.
	 */
	private void handleError() {
		if(DAO.emf == null && this.em == null) {
			throw new RuntimeException("DAO não foi devidamente inicializado.");
		}
	}
}
