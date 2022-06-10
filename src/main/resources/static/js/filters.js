const filterButton = document.querySelector(".history-nav-filters-button");
const filterMenu = document.querySelector(".history-nav-filters-menu")

const openCloseFilters = function (){
    console.log("working")
    if(filterMenu.classList.contains("hidden")){
    filterMenu.classList.remove("hidden");
    }
    else{
        filterMenu.classList.add("hidden");
    }
}
filterButton.addEventListener("click", openCloseFilters);