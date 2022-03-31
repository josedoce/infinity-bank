### Algumas dicas sobre JSP

##### Sintaxe dos elementos JSP

Scriplet: <%   %>


Comentários: <%--   --%>


Diretivas: <%@   %>


Declarações: <%!   %>


Expressões: <%=   %>


```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- É aqui onde importamos os dados --%>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Infinity Bank</title>
</head>
<body>
	<h1>Hello, tomcat!</h1>
	
	<% out.println("fulano de tal"); %>
	<%-- 
		Uso do elemento expressão 
		para usar, precisa importar.
		aqui é possivel usar variaveis.
		OBS: Só expressões não fecha com ponto e virgula.
		
	--%>
	<p>Data: <%= new Date() %></p>
	
	
	<%-- Uso de declarações --%>
	
	<%!int contador=0; %>
	<p>Visitas: <%=contador++ %></p>
</body>
</html>
```



link para achar imagens legais: https://www.iconfinder.com/

@Entity
public class CourseMaterial {
    @Id
    private Long id;
    private String url;
}

The annotation for mapping a single entity to a single other entity is, unshockingly, @OneToOne.

Before setting it up in our model, let's remember that a relationship has an owning side - preferably the side which will hold the foreign key in the database.

In our example, that would be CourseMaterial as it makes sense that it references a Course (though we could go the other way around):

@OneToOne(optional = false)
@JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
private Course course;

There is no point in having material without a course to encompass it. That's why the relationship is not optional in that direction.

Speaking of direction, let's make the relationship bidirectional, so we can access the material of a course if it has one. In the Course class, let's add:

@OneToOne(mappedBy = "course")
private CourseMaterial material;

