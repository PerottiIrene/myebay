<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<jsp:include page="../header.jsp" />
	<title>Pagina dei risultati</title>
	
</head>
<body class="d-flex flex-column h-100">
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
			  ${successMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
			  ${errorMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Lista dei risultati</h5> 
			    </div>
			    <div class='card-body'>
			    	<a class="btn btn-primary " href="${pageContext.request.contextPath}/gestioneAnnuncio/insert">Add New</a>
			    	<a href="${pageContext.request.contextPath}/gestioneAnnuncio/search" class='btn btn-outline-secondary' >
				            <i class='fa fa-chevron-left'></i> Torna alla Ricerca
				        </a>
			    
			        <div class='table-responsive'>
			            <table class='table table-striped ' >
			                <thead>
			                    <tr>
			                        <th>Testo annuncio</th>
			                        <th>Prezzo</th>
			                        <th>Data</th>
			                        <th>Azioni</th>
			                    </tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${gestioneAnnuncio_list_attribute }" var="gestioneAnnuncioItem">
									<tr>
										<td>${gestioneAnnuncioItem.testoAnnuncio }</td>
										<td>${gestioneAnnuncioItem.prezzo }</td>
										<td><fmt:formatDate type='date' value="${gestioneAnnuncioItem.data }"/></td>
										<td>
											<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/gestioneAnnuncio/show/${gestioneAnnuncioItem.id }">Visualizza</a>
										<c:if test="${!gestioneAnnuncioItem.aperto}">
											<a class="btn  btn-sm btn-outline-primary ml-2 mr-2" href="${pageContext.request.contextPath}/gestioneAnnuncio/edit/${gestioneAnnuncioItem.id }">Edit</a>
											<a class="btn  btn-sm btn-outline-danger ml-2 mr-2" href="${pageContext.request.contextPath}/gestioneAnnuncio/delete/${gestioneAnnuncioItem.id }">Elimina</a>
										    </c:if>
										</td>
									</tr>
								</c:forEach>
			                </tbody>
			            </table>
			        </div>
			   
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
	
	

	
</body>
</html>