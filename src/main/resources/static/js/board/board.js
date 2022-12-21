window.onload = () => {
    Principal.getInstance();
    CommentDataListLoader.getInstance();
    SignUpButton.getInstance();
}

class CommentDataListLoader {
    static #instance = null;

    boardCode = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);

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

        const loginFlag = Principal.getInstance().user != null;

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
                                ${loginFlag ? `<button class="show-reply-button" type="button">답글달기</button>` : ``}
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
                                ${loginFlag ? `<button class="show-reply-button" type="button">답글달기</button>` : ``}
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

        if(loginFlag) {
            commentUl.innerHTML += `
                <li class="write-comment-li">
                    <textarea></textarea>
                    <button class="write-comment-button" type="button">작성</button>
                </li>
            `;

            this.setWriteReplyButtonClickEvent(commentList);
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

    setWriteReplyButtonClickEvent(commentList) {
        const writeReplyButtons = document.querySelectorAll(".write-reply-button");

        writeReplyButtons.forEach((button, index) => {
            button.onclick = () => this.writeReplyRequest(commentList[index], index);
        })
    }

    writeReplyRequest(comment, index) {
        const commentData = this.getCommentData(comment, index);
        $.ajax({
            async: false,
            type: "post",
            url: `/api/v1/commnet/`,
            contentType: "application/json",
            data: JSON.stringify(commentData),
            dataType: "json",
            success: (response) => {
                if(response.data) {
                    location.replace(`/board/${this.boardCode}`);
                }
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })
    }

    getCommentData(comment, index) {
        const replyData = {
            "userCode": Principal.getInstance().user.userCode,
            "boardCode": this.boardCode,
            "comment": document.querySelectorAll(".write-reply-li")[index].value,
            "parentCode": comment.commentCode,
            "parentUserCode": comment.userCode
        }
        
        return replyData;
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

class SignUpButton {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new SignUpButton();
        }

        return this.#instance;
    }

    constructor() {
        this.setSignUpButtonClickEvent();
    }

    setSignUpButtonClickEvent() {
        const signUpButton = document.querySelector(".sign-up-button-div button");

        signUpButton.onclick = () => this.signUpRequest();
    }

    signUpRequest() {
        let userData = this.getUserData();

        $.ajax({
            async: false,
            type: "post",
            url: `/api/v1/auth/user`,
            contentType: "application/json",
            data: JSON.stringify(userData),
            dataType: "json",
            success: (response) => {
                if(response.data) {
                    location.replace(`/board/${CommentDataListLoader.getInstance().boardCode}`);
                }
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })
    }

    getUserData() {
        let userData = {
            "userName" : document.querySelector(".user-name").value,
            "userId" : document.querySelector(".user-id").value,
            "userPassword" : document.querySelector(".user-password").value
        };

        return userData;
    }
}

class Principal {
    static #instance = null;

    user = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new Principal();
        }

        return this.#instance;
    }

    constructor() {
        this.setUser();
    }

    setUser() {
        this.user = this.getUser();
    }

    getUser() {
        let user = null;

        $.ajax({
            async: false,
            type: "get",
            url: "/api/v1/auth/principal",
            dataType: "json",
            success: (response) => {
                user = response.data;
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })

        return user;
    }
}