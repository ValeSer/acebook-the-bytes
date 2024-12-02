newPostBtn = document.querySelector("#new_post_btn");
newPostBackdrop = document.querySelector(".new_post_backdrop");
newPostModal = document.querySelector(".new_post_modal");
newPostModalCloseBtn = document.querySelector(".new_post_modal_close");
newPostModalContent = document.querySelector("#modalContent");
newPostModalAddPhoto = document.querySelector("#modalPhotoUrl");

newPostBtn.addEventListener("click", showModal);
newPostBackdrop.addEventListener("click", closeModal);
newPostModalCloseBtn.addEventListener("click", closeModal);

function showModal() {
    newPostBackdrop.classList.remove("hidden");
    newPostModal.classList.remove("hidden");
    newPostModalContent.value = "";
    newPostModalAddPhoto.value = "";
}

function closeModal() {
    newPostBackdrop.classList.add("hidden");
    newPostModal.classList.add("hidden");
}