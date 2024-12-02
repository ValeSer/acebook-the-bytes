document.addEventListener("DOMContentLoaded", function() {
    postLikeButtons = document.querySelectorAll(".post_like_button");
    testLike = document.querySelector(".posts_header");

    postLikeButtons.forEach(button => {
        button.addEventListener("click", toggleLike);
    });

    function toggleLike(e) {
        e.preventDefault();
        const postId = this.dataset.postId;
        const isLiked = this.dataset.liked === "true";
        const icon = this.querySelector('i');
        const likeCountElement = document.querySelector(`#like_count_${postId}`);
        console.log(likeCountElement);
        fetch(`/post/${postId}/` + (isLiked ? 'postunlike' : 'postlike'), {
            method: isLiked ? 'DELETE' : 'POST'
        }).then(response => {
            if (response.ok) {
                this.dataset.liked = (!isLiked).toString();
                icon.classList.toggle('fa-solid', !isLiked);
                icon.classList.toggle('fa-regular', isLiked);

                let currentLikeCount = parseInt(likeCountElement.textContent);
                likeCountElement.textContent = isLiked ? currentLikeCount - 1 : currentLikeCount + 1;
            }
        });
    }
});
