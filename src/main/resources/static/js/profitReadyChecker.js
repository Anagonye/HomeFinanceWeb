const valueInputProfit = document.querySelector(".profit-input-value");
const nameInputProfit = document.querySelector(".profit-input-name");
const categoryInputProfit = document.querySelector(".profit-input-category");
const dateInputProfit = document.querySelector(".profit-input-date");
const profitBtnSave = document.querySelector(".profit-btn-save");

const addReadyClass4 = () => {
    profitBtnSave.classList.add("profit-ready-to-save");
    console.log(true);
};

const removeReadyClass4 = () => {
    profitBtnSave.classList.remove("profit-ready-to-save");
    console.log(false);
};

const showChanges4 = function () {

    let areNotEmpty =
        nameInputProfit.value.trim().length &&
        valueInputProfit.value.trim().length &&
        categoryInputProfit.value.trim().length &&
        dateInputProfit.value.trim().length;

    let oneIsEmpty =
        nameInputProfit.value.trim().length ||
        valueInputProfit.value.trim().length ||
        categoryInputProfit.value.trim().length || dateInputProfit.value.trim().length;

    if (areNotEmpty) {
        addReadyClass4();
    } else if (oneIsEmpty) {
        removeReadyClass4();
    }
};

document.addEventListener("keyup", showChanges4);
document.addEventListener("keypress", showChanges4);
document.addEventListener("keydown", showChanges4);
profitBtnSave.addEventListener("click", showChanges4)



nameInputProfit.addEventListener("blur", showChanges4);
valueInputProfit.addEventListener("blur", showChanges4);
categoryInputProfit.addEventListener("blur", showChanges4);
dateInputProfit.addEventListener("blur", showChanges4);
profitBtnSave.addEventListener("mouseover", showChanges4);

