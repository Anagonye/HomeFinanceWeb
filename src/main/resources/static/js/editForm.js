const editValueInput = document.querySelector(".edit-input-value")
const editNameInput = document.querySelector(".edit-input-name")
const editCategoryInput = document.querySelector(".edit-input-category")
const editDateInput = document.querySelector(".edit-input-date")
const editBtnSave = document.querySelector(".edit-btn-save")


const addReadyClass3 = () => {
    editBtnSave.classList.add("expense-ready-to-save");
    console.log(true);
};

const removeReadyClass3 = () => {
    editBtnSave.classList.remove("expense-ready-to-save");
    console.log(false);
};

const showChanges3 = function () {

    let areNotEmpty =
        editValueInput.value.trim().length &&
        editCategoryInput.value.trim().length &&
        editNameInput.value.trim().length &&
       editDateInput.value.trim().length;

    let oneIsEmpty =
        editNameInput.value.trim().length ||
        editCategoryInput.value.trim().length ||
        editValueInput.value.trim().length || editDateInput.value.trim().length;

    if (areNotEmpty) {
        addReadyClass3();
    } else if (oneIsEmpty) {
        removeReadyClass3();
    }
};

document.addEventListener("keyup", showChanges3);
document.addEventListener("keypress", showChanges3);
document.addEventListener("keydown", showChanges3);
editButton.addEventListener("click", showChanges3)



editNameInput.addEventListener("blur", showChanges3);
editDateInput.addEventListener("blur", showChanges3);
editValueInput.addEventListener("blur", showChanges3);
editBtnSave.addEventListener("mouseover", showChanges3);
