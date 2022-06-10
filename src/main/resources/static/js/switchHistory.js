
const historySwitchBtn = document.querySelector(".history-nav-switch-btn");
const historySwitchMenu = document.querySelector(".history-nav-switch-menu");

const openCloseMenu = function (){
    if(historySwitchMenu.classList.contains("hidden")){
        historySwitchMenu.classList.remove("hidden");
    }
    else {
        historySwitchMenu.classList.add("hidden");
    }

}
historySwitchBtn.addEventListener("click", openCloseMenu);

const switchHistory =  function (){

    const now = new Date(Date.now());

    console.log("year "+now.getFullYear())
    console.log("month "+now.getMonth())
    const year = now.getFullYear();
    const month = now.getMonth() +1;

    location.href = "/profits/history/"+year+"/"+month;


}
historySwitchMenu.addEventListener("click", switchHistory);

