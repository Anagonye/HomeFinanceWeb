"use strict";

const makeDarker = document.querySelector(".make-darker");

const addForm = document.querySelector(".add-form-box");

const closeAddFormBtn = document.querySelectorAll(".add-form-exit-btn");





const addFormMenu = document.querySelector(".add-form-menu");

const addFormSelectOption = document.querySelector(".add-form-select-menu");

const addProfitForm = document.querySelector(".add-profit-form-box");


const addFormSelectOptionExpense = document.querySelector(".add-form-select-menu-expense");
const addProfitFormMenu = document.querySelector(".add-profit-form-menu");

const addFormContent = document.querySelectorAll(".add-form-content")

const btnSave = document.querySelector(".add-form-btn-save")




const removeHiddenClass = function () {
  makeDarker.classList.remove("hidden");
  addForm.classList.remove("hidden");
};

const removeHiddenClassProfits = function (){
  makeDarker.classList.remove("hidden");
  addProfitForm.classList.remove("hidden");
}

const addHiddenClass = function () {
  makeDarker.classList.add("hidden");
  addForm.classList.add("hidden");
  addProfitForm.classList.add("hidden");
  duplicateForm.classList.add("hidden")
  editForm.classList.add("hidden")
};

const removeHiddenClassFromMenu = function (){
  if(addFormSelectOption.classList.contains("hidden")){
  addFormSelectOption.classList.remove("hidden");
  }
  else{
    addFormSelectOption.classList.add("hidden");
  }

};

const switchHiddenClass = function (){
  addFormSelectOption.classList.add("hidden");
  addForm.classList.add("hidden");
  addProfitForm.classList.remove("hidden");



}
const removeHiddenFromOptionExpense = function ()  {
  if(addFormSelectOptionExpense.classList.contains("hidden")) {
    addFormSelectOptionExpense.classList.remove("hidden");
  }
  else
    addFormSelectOptionExpense.classList.add("hidden");

}

const switchHiddenClassProfit = function (){
  addFormSelectOptionExpense.classList.add("hidden");
  addProfitForm.classList.add("hidden");
  addForm.classList.remove("hidden");
}

const closeOptionMenu = function () {
  addFormSelectOption.classList.add("hidden");
  addFormSelectOptionExpense.classList.add("hidden");
}








for(let i = 0; i<closeAddFormBtn.length; i++){
  closeAddFormBtn[i].addEventListener("click", addHiddenClass);
}



addFormMenu.addEventListener("click", removeHiddenClassFromMenu);
addFormSelectOption.addEventListener("click", switchHiddenClass);
addProfitFormMenu.addEventListener("click", removeHiddenFromOptionExpense);
addFormSelectOptionExpense.addEventListener("click", switchHiddenClassProfit);

for(let i = 0; i<addFormContent.length; i++){
  addFormContent[i].addEventListener("click", closeOptionMenu);
}


const valueInput = document.querySelector(".add-form-value-input");
const nameInput = document.querySelector(".add-form-name-input");
const categoryInput = document.querySelector(".add-form-category-input");
const dateInput = document.querySelector(".add-form-date-input");

const addFormSaveBtn = document.querySelector(".add-form-btn-save");

const addReadyClass = () => {
  addFormSaveBtn.classList.add("expense-ready-to-save");
  console.log(true);
};

const removeReadyClass = () => {
  addFormSaveBtn.classList.remove("expense-ready-to-save");
  console.log(false);
};

const showChanges = function () {
  let areNotEmpty =
    valueInput.value.trim().length &&
    nameInput.value.trim().length &&
    categoryInput.value.trim().length &&
    dateInput.value.trim().length;

  let oneIsEmpty =
    valueInput.value.trim().length ||
    nameInput.value.trim().length ||
    categoryInput.value.trim().length || dateInput.value.trim().length;

  if (areNotEmpty) {
    addReadyClass();
  } else if (oneIsEmpty) {
    removeReadyClass();
  }
};

document.addEventListener("keyup", showChanges);
document.addEventListener("keypress", showChanges);
document.addEventListener("keydown", showChanges);

valueInput.addEventListener("change", showChanges);

btnSave.addEventListener("click", showChanges)



nameInput.addEventListener("blur", showChanges);
valueInput.addEventListener("blur", showChanges);
categoryInput.addEventListener("blur", showChanges);
dateInput.addEventListener("blur", showChanges);
btnSave.addEventListener("mouseover", showChanges);


function getExpense(id){

  location.href = "/expenses/"+id;

}

function getProfit(id){

  location.href = "/profits/"+id;

}

function deleteExpense(id){
  location.href = "/expense/delete/"+id;
}































