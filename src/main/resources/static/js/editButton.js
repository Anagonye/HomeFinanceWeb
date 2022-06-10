const editButton = document.querySelector(".edit-btn");
const editForm = document.querySelector(".edit-form-box");

const openEditForm = function (){
    makeDarker.classList.remove("hidden");
    editForm.classList.remove("hidden");
}

editButton.addEventListener("click", openEditForm);