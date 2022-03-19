package infinitybank.controller;

import java.io.IOException;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import infinitybank.model.Account;
import infinitybank.model.Client;
import infinitybank.model.DAO;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = {
"/main",
"/newaccount",
"/editaccount",
"/delete/*",
"/relatory",
"/money",
"/test"
})
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
       
    
    /**
     * Instantiates a new controller.
     */
    public Controller() {
        super();
    }

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		if(action.equals("/main")) {
			this.allAccounts(request, response);
		}
		if(action.equals("/delete")) {
			this.deleteAccount(request, response);
		}
		
		if(action.equals("/relatory")) {
			this.generateRelatory(request, response);
		}
	}
	
	/**
	 * Do post.
	 *
	 * @param req the req
	 * @param resp the resp
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String name = req.getParameter("cname");
		String password = req.getParameter("cpassword");
		if(action.equals("/newaccount")) {
			this.createAccount(name, password);
			resp.sendRedirect("main");
		}
		if(action.equals("/editaccount")) {
			Integer id = Integer.valueOf(req.getParameter("cid"));
			this.updateAccount(id, name, password);
			resp.sendRedirect("main");
		}
		if(action.equals("/money")){
			this.updateAmount(req, resp);
			resp.sendRedirect("main");
		}
	}
	
	/**
	 * Creates the account.
	 *
	 * @param name the name
	 * @param password the password
	 * @return the client
	 */
	//CREATE - criar cliente
	private Client createAccount(String name, String password) {
		DAO<Client> dao = new DAO<>();
		Account account = new Account(0.0);
		Client client = new Client(name, password, account);
		return dao.save(client).close();
	}
	
	/**
	 * All accounts.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//READ - listar cliente
	private void allAccounts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAO<Client> dao = new DAO<>(Client.class);
		List<Client> clients = dao.getAll(10, 0);
		request.setAttribute("clients", clients);
		//responsavel por encaminhar o objeto clients para o frontend jsp
		RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Update account.
	 *
	 * @param id the id
	 * @param name the name
	 * @param password the password
	 */
	//UPDATE - editar client
	private void updateAccount(Integer id, String name, String password) {
		DAO<Client> dao = new DAO<>(Client.class);
		Client client = dao.getById(id);
		client.setClientName(name);
		client.setPassword(password);
		dao.update(id, client).close();
	}
	
	/**
	 * Delete account.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//DELETE - delete client
	private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.valueOf(request.getPathInfo().replace("/", ""));
		DAO<Client> dao = new DAO<>(Client.class);
		dao.delete(id).close();
		response.sendRedirect("/main");
	}
	
	/**
	 * Generate relatory.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//gerar pdf de um relatório
	protected void generateRelatory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document document = new Document();
		DAO<Client> dao = new DAO<>(Client.class);
		try {
			//tipo de conteudo retornado
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename="+"clientes.pdf");
			
			//criando o documento
			PdfWriter.getInstance(document, response.getOutputStream());
			
			//abrir o documento -> conteúdo
			document.open();
			document.add(new Paragraph("Lista de clientes: "));
			document.add(new Paragraph(" "));//quebra de linha
			
			//criar uma tabela
			PdfPTable table = new PdfPTable(3); //o 3 indica o numero de tabelas.
			PdfPCell col1 = new PdfPCell(new Paragraph("Id"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Em conta"));
			table.addCell(col1);
			table.addCell(col2);
			table.addCell(col3);
			//popular a tabela com os contatos
			List<Client> clients = dao.getAll(10, 0);
			clients.stream().forEach((client)->{
				table.addCell(client.getId().toString());
				table.addCell(client.getClientName());
				table.addCell(client.getAccount().getMoney().toString());
			});
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			document.close();
		}
	}
	
	/**
	 * Update amount.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void updateAmount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.valueOf(request.getParameter("cid"));
		Double money = Double.valueOf(request.getParameter("camount").replace(",", "."));
		DAO<Client> dao = new DAO<>(Client.class);
		Client client = dao.getById(id);
		client.getAccount().setMoney(money);
		dao.update(id, client).close();
	}
}
