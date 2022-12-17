window.onload = () => {
    CommentDataListLoader.getInstance();
}

class CommentDataListLoader {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new CommentDataListLoader();
        }

        return this.#instance;
    }

    constructor() {
        this.setCommentList();
    }

    setCommentList() {
        const commentUl = document.querySelector(".comment-ul");
        const commentList = this.getCommentList();

        if(commentList != null) {
            ObjectManager.getInstance().clearDomObject(commentUl);
            let replyCount = 0;
            let newCommentFlag = false;
            let replyUl = null;

            commentList.forEach(comment => {
                const replyFlag = comment.replyFlag;

                if(replyFlag) {
                    if(newCommentFlag) {
                        commentUl.innerHTML += `
                            <ul class="reply-ul visible"></ul>
                        `;
                        
                        replyUl = document.querySelectorAll(".reply-ul")[replyCount];
                        replyCount++;
                    }


                    replyUl.innerHTML += `
                        <li class="reply-li">
                            <div class="comment-title">
                                <p class="writer-p">${comment.userName}</p>
                                <p class="create-date-p">${comment.createDate}</p>
                            </div>
                            <span>${comment.comment}</span>
                            <div>
                                <button class="show-reply-button" type="button">답글달기</button>
                            </div>
                        </li>
                        <li class="write-reply-li visible">
                            <textarea></textarea>
                            <button class="write-reply-button" type="button">작성</button>
                            <button class="cancel-reply-button" type="button">취소</button>
                        </li>
                    `;

                    newCommentFlag = false;

                }else {
                    commentUl.innerHTML += `
                        <li>
                            <div class="comment-title">
                                <p class="writer-p">${comment.userName}</p>
                                <p class="create-date-p">${comment.createDate}</p>
                            </div>
                            <span>${comment.comment}</span>
                            <div>
                                <button class="show-reply-button" type="button">답글달기</button>
                                ${comment.haveReplyFlag ? `<button class="more-comment-button" type="button">답글보기</button>` : ``}
                            </div>
                        </li>
                        <li class="write-reply-li visible">
                            <textarea></textarea>
                            <button class="write-reply-button" type="button">작성</button>
                            <button class="cancel-reply-button" type="button">취소</button>
                        </li>
                    `;

                    newCommentFlag = true;
                }

                
            })
        }else {
            commentUl.innerHTML = `
            <li>
                <span>댓글이 존재하지 않습니다.</span>
            </li>
            `;

        }

        this.setMoreCommentButtonClickEvent();
        this.setShowReplyButtonClickEvent();
        this.setCancelReplyButtonClickEvent();
    }

    getCommentList() {
        let commentList = null;
        const boardCode = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);

        $.ajax({
            async: false,
            type: "get",
            url: `/api/v1/comment/list/${boardCode}`,
            dataType: "json",
            success: (response) => {
                commentList = response.data;
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        });

        return commentList;
    }

    setMoreCommentButtonClickEvent() {
        const moreCommentButtonItems = document.querySelectorAll(".more-comment-button");
        const replyUlItems = document.querySelectorAll(".reply-ul");

        moreCommentButtonItems.forEach((button, index) => {
            const replyUl = replyUlItems[index];

            button.onclick = () => {
                replyUl.classList.contains("visible") ? this.showMoreReplyList(button, replyUl) : this.hideMoreReplyList(button, replyUl);
            }
        })
    }

    setShowReplyButtonClickEvent() {
        const showReplyButtonItems = document.querySelectorAll(".show-reply-button");
        const writeReplyLiItems = document.querySelectorAll(".write-reply-li");

        showReplyButtonItems.forEach((button, index) => {
            button.onclick = () => this.showWriteReplyLi(writeReplyLiItems[index]);
        })
    }

    showMoreReplyList(button, replyUl) {
        ObjectManager.getInstance().removeVisibleClass(replyUl);
        button.textContent = "답글접기";

        this.setMoreCommentButtonClickEvent();
    }

    hideMoreReplyList(button, replyUl) {
        ObjectManager.getInstance().addVisibleClass(replyUl);
        button.textContent = "답글보기";

        this.setMoreCommentButtonClickEvent();
    }

    showWriteReplyLi(writeReplyLi) {
        ObjectManager.getInstance().removeVisibleClass(writeReplyLi);
    }

    setCancelReplyButtonClickEvent() {
        const cancelReplyButtonItems = document.querySelectorAll(".cancel-reply-button");
        const writeReplyLiItems = document.querySelectorAll(".write-reply-li");

        cancelReplyButtonItems.forEach((button, index) => {
            button.onclick = () => ObjectManager.getInstance().addVisibleClass(writeReplyLiItems[index]);
        })
    }
}

class ObjectManager {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ObjectManager();
        }

        return this.#instance;
    }

    clearDomObject(domObject) {
        domObject.innerHTML = "";
    }

    addVisibleClass(domObject) {
        domObject.classList.add("visible");
    }

    removeVisibleClass(domObject) {
        domObject.classList.remove("visible");
    }
}