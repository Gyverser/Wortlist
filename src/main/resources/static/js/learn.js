document.addEventListener("DOMContentLoaded", () => {

    const showMeaningBtn = document.getElementById("showMeaningBtn");
    const meaning = document.getElementById("meaning");
    const nextWordBtn = document.getElementById("nextWordBtn");

    showMeaningBtn.addEventListener("click", () => {
        meaning.style.display = "block";
        showMeaningBtn.style.display = "none";
    });

    nextWordBtn.addEventListener("click", () => {
        window.location.href = "/learn";
    });

});
