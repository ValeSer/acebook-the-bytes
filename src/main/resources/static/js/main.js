document.addEventListener("DOMContentLoaded", function() {
    likeButtons = document.querySelectorAll(".like_button");

    likeButtons.forEach(button => {
        button.addEventListener("click", toggleLike);
    });

    function toggleLike(e) {
        e.preventDefault();
        const postId = this.dataset.postId;
        const commentId = this.dataset.commentId;

        // Determine if is post or comment (double negation so if postId exists, will be true)
        const isPost = !!postId;

        const id = isPost ? postId : commentId;
        const isLiked = this.dataset.liked === "true";
        const icon = this.querySelector('i');
        let likeCountElement;

        if (isPost) {
            likeCountElement = document.querySelector(`#post_like_count_${id}`);
        } else {
            likeCountElement = document.querySelector(`#comment_like_count_${id}`);
        }

        const route = isPost ? `/post/${id}/` : `/comment/${id}/`;

        fetch(route + (isLiked ? 'unlike' : 'like'), {
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
