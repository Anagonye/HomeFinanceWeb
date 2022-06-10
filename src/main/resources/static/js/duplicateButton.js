
const duplicateButton = document.querySelector(".duplicate");
const duplicateForm = document.querySelector(".duplicate-form-box");

const openDuplicateForm = function (){
    makeDarker.classList.remove("hidden");
    duplicateForm.classList.remove("hidden");
}

duplicateButton.addEventListener("click", openDuplicateForm);