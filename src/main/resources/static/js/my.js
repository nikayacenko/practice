document.addEventListener("DOMContentLoaded", function() {
    const myVacAddButton = document.getElementById("MyVacAdd");
    const formContainer1 = document.getElementById('formSearchingContainer1');

    myVacAddButton.addEventListener('click', () => {
        fetch('/add')  // URL endpoint, который возвращает addBookForm.html
            .then(response => response.text())
            .then(html => {
                formContainer1.innerHTML = html;
            })
            .catch(error => {
                console.error('Error fetching form:', error);
                formContainer1.innerHTML = '<p>Ошибка загрузки формы.</p>';
            });
    });
});