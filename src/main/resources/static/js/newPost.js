document.addEventListener("DOMContentLoaded", () => {
    const newPostBtn = document.querySelector("#new_post_btn");
    const newPostBackdrop = document.querySelector(".new_post_backdrop");
    const newPostModal = document.querySelector(".new_post_modal");
    const newPostModalCloseBtn = document.querySelector(".new_post_modal_close");
    const newPostModalContent = document.querySelector("#modalContent");
    const newPostModalAddPhoto = document.querySelector("#modalPhotoUrl");

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

    // Message for delay whilst post is uploaded...
    const newPostForm = document.querySelector("#new_post_form");
    const newPostSubmitBtn = document.querySelector("#new_post_modal_submit");

    newPostForm.addEventListener("submit", postSending);

    function postSending(e) {
        e.preventDefault();
        newPostSubmitBtn.value = "Posting...";
        newPostSubmitBtn.disabled = true;
        this.submit();
    }
})