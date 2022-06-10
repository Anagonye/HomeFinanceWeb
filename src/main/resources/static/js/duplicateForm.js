const duplicateValueInput = document.querySelector(".duplicate-input-value")
const duplicateNameInput = document.querySelector(".duplicate-input-name")
const duplicateCategoryInput = document.querySelector(".duplicate-input-category")
const duplicateDateInput = document.querySelector(".duplicate-input-date")
const duplicateBtnSave = document.querySelector(".duplicate-btn-save")


const addReadyClass2 = () => {
    duplicateBtnSave.classList.add("expense-ready-to-save");
    console.log(true);
};

const removeReadyClass2 = () => {
    duplicateBtnSave.classList.remove("expense-ready-to-save");
    console.log(false);
};

const showChanges2 = function () {

    let areNotEmpty =
        duplicateValueInput.value.trim().length &&
        duplicateCategoryInput.value.trim().length &&
        duplicateNameInput.value.trim().length &&
        duplicateDateInput.value.trim().length;

    let oneIsEmpty =
        duplicateNameInput.value.trim().length ||
        duplicateValueInput.value.trim().length ||
        duplicateDateInput.value.trim().length || duplicateCategoryInput.value.trim().length;

    if (areNotEmpty) {
        addReadyClass2();
    } else if (oneIsEmpty) {
        removeReadyClass2();
    }
};

document.addEventListener("keyup", showChanges2);
document.addEventListener("keypress", showChanges2);
document.addEventListener("keydown", showChanges2);
duplicateButton.addEventListener("click", showChanges2)



duplicateNameInput.addEventListener("blur", showChanges2);
duplicateCategoryInput.addEventListener("blur", showChanges2);
duplicateDateInput.addEventListener("blur", showChanges2);
duplicateBtnSave.addEventListener("mouseover", showChanges2)
