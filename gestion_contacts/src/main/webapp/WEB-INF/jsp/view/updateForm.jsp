<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>
<title>Update form</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
	crossorigin="anonymous">

<style>
h3 {
	margin-top: 20px;
}

#navbarNav div {
	height: 0;
}

#navbarNav form {
	margin: 0;
	padding: 0;
}

form {
	margin-bottom: 60px;
	margin-top: 10px;
	padding: 10px;
}
</style>

</head>
<body>
	<div class="container">


		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="${pageContext.request.contextPath}/showForm">Home</a></li>





					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/showForm">Add Persons
					</a></li>


					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/manageContacts">Manage
							Contacts </a></li>



					<li class="nav-item"><form
							action="${pageContext.request.contextPath}/searchContact"
							class="d-flex" method="POST">
							<input name="nid" class="form-control me-2" type="search"
								placeholder="Nom" aria-label="Search">
							<button class="btn btn-outline-success" type="submit">Search</button>
						</form></li>
					<li class="nav-item"><form
							action="${pageContext.request.contextPath}/searchContactPer"
							class="d-flex" method="POST">
							<input name="nid" class="form-control me-2" type="search"
								placeholder="Numéro personnel" aria-label="Search">
							<button class="btn btn-outline-success" type="submit">Search</button>
						</form></li>
					<li class="nav-item"><form
							action="${pageContext.request.contextPath}/searchContactPro"
							class="d-flex" method="POST">
							<input name="nid" class="form-control me-2" type="search"
								placeholder="Numéro professionel" aria-label="Search">
							<button class="btn btn-outline-success" type="submit">Search</button>
						</form></li>
						
					



				</ul>



			</div>

		</nav>


		<div>
			<h3>Update Contact</h3>
		</div>
		<div>



			<f:form action="${pageContext.request.contextPath}/updateContact"
				method="POST" modelAttribute= "contactModel">
				<f:hidden path="id" />
				<div class="row">
					<div class="col">
						<label>Nom</label>
						<f:input path="nom" type="text" class="form-control"
							placeholder="Nom" />
						<f:errors path="nom" class="text-danger" />
					</div>
					<div class="col">
						<label>numéro professionnel</label>
						<f:input path="numeroPro" type="text" class="form-control"
							placeholder="Numero professionnel" />
						<f:errors path="numeroPro" class="text-danger" />
					</div>
					
					
				</div>


				<div class="row">
				    <div class="col">
						<label>Prenom</label>
						<f:input path="prenom" type="text" class="form-control"
							placeholder="Prenom" />
						<f:errors path="prenom" class="text-danger" />
					</div>
					<div class="col">
						<label>numéro personnel</label>
						<f:input path="numeroPer" type="text" class="form-control"
							placeholder="Numero personnel" />
						<f:errors path="numeroPer" class="text-danger" />
					</div>
					
					
				</div>


				<div class="row">
					<div class="col">
						<label>Email personnel</label>
						<f:input path="email_personnel" class="form-control" placeholder="Email personnel" />
						<f:errors path="email_personnel" class="text-danger" />
					</div>
					<div class="col">
						<legend class="col-form-label col-sm-2 pt-0">genre</legend>
						<div class="form-check">
							<f:radiobutton path="genre" class="form-check-input"
								value="Femme" />
							<label class="form-check-label">Femme</label>
						</div>
						<div class="form-check">
							<f:radiobutton path="genre" class="form-check-input"
								value="Homme " />
							<label class="form-check-label">Homme</label>
						</div>
						<f:errors path="genre" class="text-danger" />
						<%-- <form:radiobuttons path="abc" items="${xyz}"/>   --%>

					</div>
					
				
				</div>



				<div class="row">
					<div class="col">
						<label>Adresse</label>
						<f:input path="adresse" type="text" class="form-control"
							placeholder="...., Morocco" />
						<f:errors path="adresse" class="text-danger" />
					</div>
					<div class="col">
						<label>Email professionnel</label>
						<f:input path="email_professionnel" class="form-control" placeholder="Email professionnel" />
						<f:errors path="email_professionnel" class="text-danger" />
					</div>
					
					
					
				</div>

				<div style="text-align: right">
					<button type="submit" class="btn btn-primary">Update</button>
					<button type="reset" class="btn btn-secondary">Rest</button>
				</div>

			</f:form>
		</div>


	</div>
</body>
</html>