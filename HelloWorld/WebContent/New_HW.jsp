<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<H1>Hello и кажется получилось!</H1>
<form name="test" method="post" action="" >
<p>Комментарий: <input name="name" value="${name}"></input></p>
<p>Комментарий: <input name="name1" value="${name1}"></input></p>
<p>Комментарий: <input name="name2" value="${name2}"></input></p>

<input type="submit" value="Отправить" />
</form>
<form name="test" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<p>Комментарий: <input type="file" name="txtfile"></input></p>
<input type="submit" value="Загрузить" />
</form>
</body>
</html>