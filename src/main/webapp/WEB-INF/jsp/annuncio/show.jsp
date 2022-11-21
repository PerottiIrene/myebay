<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza elemento</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Id:</dt>
					  <dd class="col-sm-9">${show_annuncio_attr.id}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Testo annuncio:</dt>
					  <dd class="col-sm-9">${show_annuncio_attr.testoAnnuncio}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Prezzo:</dt>
					  <dd class="col-sm-9">${show_annuncio_attr.prezzo}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data annuncio:</dt>
					  <dd class="col-sm-9">${show_annuncio_attr.data}</dd>
			    	</dl>
			    	
			    	
			    	
					</div>
			    	
			    <!-- end card body -->
			    </div>
			    
			   <div class='card-footer'>
					<form action="${pageContext.request.contextPath}/annuncio/compra " method="post">
					<input type="hidden" name="idAnnuncio" value="${show_annuncio_attr.id}">
					 	<button type="submit" name="submit" value="submit" id="submit" class="btn btn-success">Compra</button>
					 	
			    
			  
			        <a href="${pageContext.request.contextPath }/annuncio/list" class='btn btn-outline-secondary' style='width:80px'>
			            <i class='fa fa-chevron-left'></i> Back
			        </a>
			          </form>
			    </div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>