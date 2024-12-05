// Submit comment on post by hitting enter
const submitButton = document.querySelector("#comment_submit");
const commentInput = document.querySelector("#comment");
const commentForm = commentInput.closest(".comment_form");

commentInput.addEventListener("keydown", submitCommentFormEnter);
submitButton.addEventListener("click", submitCommentFormClick);

function submitCommentFormEnter(e, form) {
    if (event.key === 'Enter') {
        e.preventDefault();
        commentForm.submit();
    }
}

function submitCommentFormClick () {
    commentForm.submit();
};