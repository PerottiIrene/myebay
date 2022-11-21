<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Modifica Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="edit_annuncio_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo elemento</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form:form modelAttribute="edit_annuncio_attr" method="post" action="${pageContext.request.contextPath}/gestioneAnnuncio/update" novalidate="novalidate" class="row g-3">
								<input type="hidden" name="id" value="${edit_annuncio_attr.id }">
								<input type="hidden" name="data" value="${edit_annuncio_attr.data }">
							
								<div class="col-md-6">
									<label for="testoAnnuncio" class="form-label">Testo annuncio <span class="text-danger">*</span></label>
									<spring:bind path="testoAnnuncio">
										<input type="text" name="testoAnnuncio" id="testoAnnuncio" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il nome" value="${edit_annuncio_attr.testoAnnuncio }" required>
									</spring:bind>
									<form:errors  path="testoAnnuncio" cssClass="error_field" />
								</div>
								
								<div class="col-md-6">
									<label for="cognome" class="form-label">Prezzo <span class="text-danger">*</span></label>
									<spring:bind path="prezzo">
										<input type="text" name="prezzo" id="prezzo" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il cognome" value="${edit_annuncio_attr.prezzo }" required>
									</spring:bind>
									<form:errors  path="prezzo" cssClass="error_field" />
									</div>
									
								<div class="col-md-3">
									<label for="aperto" class="form-label">Stato <span class="text-danger">*</span></label>
								    <spring:bind path="aperto">
									    <select class="form-select ${status.error ? 'is-invalid' : ''}" id="aperto" name="aperto" required>
									    	<option value="" selected> - Selezionare - </option>
									    	<option value="true" ${edit_annuncio_attr.aperto == 'true'?'selected':''}>Aperto</option>
									      	<option value="false" ${edit_annuncio_attr.aperto == 'false'?'selected':''}>Chiuso</option>
								    	</select>
								    </spring:bind>
								    <form:errors  path="aperto" cssClass="error_field" />
								</div>

								 	
							<%--  checkbox categorie 	--%>
								<%-- facendolo con i tag di spring purtroppo viene un po' spaginato quindi aggiungo class 'a mano'	--%>
								<div class="col-md-6 form-check" id="categorieDivId">
									<p style="margin-left: 30px;"><b>Categorie:</b></p>
									<form:checkboxes itemValue="id" itemLabel="descrizione"  element="div class='form-check'" items="${categorie_totali_attr}" path="categorieIds" />
								</div>
								<script>
									$(document).ready(function(){$("#categorieDivId :input").each(function () {
										$(this).addClass('form-check-input'); 
									});
									$("#categorieDivId label").each(function () {
										$(this).addClass('form-check-label'); 
									});
									
								});
							</script>
								
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<a class="btn btn-outline-secondary ml-2" href="${pageContext.request.contextPath }/gestioneAnnuncio/list">Torna alla Lista</a>
								</div>
		
						</form:form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>