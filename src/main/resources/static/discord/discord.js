const posts = [
    {
        title: "소 개",
        content: "안녕하세요. Bbot 입니다. 만나서 반가워요! \n\n" +
            "<a href='https://discord.com/oauth2/authorize?client_id=1272408626174365827&permissions=8&integration_type=0&scope=bot' target='_blank'>봇 추가하기</a>"
    },
    {
        title: "사용법",
        content: "        1. !bot 를 사용하여 저장된 명령들을 실행할 수 있습니다.\n(ex : !bot 안녕 )\n\n" +
            "                2. !명령어 를 사용하여 저장된 명령어 목록을 볼 수 있습니다.\n (ex : !명령어 )\n\n" +
            "                3. !명령저장 를 사용하여 명령어를 저장할 수 있습니다.\n (!명령저정 (명령어) (명령 답어) )\n (ex : !명령저장 안녕 반가워요)\n\n" +
            "                4. !명령삭제 를 사용하여  저장된 명령어를 삭제할 수 있습니다.\n (ex : !명령삭제 안녕 )\n\n" +
            "                5. !인장 을 사용하여  인장주작하는 을 볼 수 있습니다.\n (ex : !인장 )\n\n" +
            "                6. !메이플 을 사용하여 캐릭터의 정보를 볼 수 있습니다.\n (!명령어 (캐릭터이름) )\n                (ex : !메이플 아델 )\n\n" +
            "                7. !스타포스 를 사용하여 계정의 스타포스에 사용한 비용을 확인할 수 있습니다.\n 자신의 API가 필요합니다. API는 한번 저장하면 영구적으로 저장 가능합니다.\n ( ex : !스타포스 ) ( ex : !api저장 live_apikey.... )\n ( ex : !api삭제 )\n\n" +
            "                8. !잠재능력 을 사용하여 계정의 잠재능력에 사용한 비용을 알 수 있습니다. \n자신의 API가 필요합니다. \n( ex : !잠재능력)\n\n"+
            "                9. !큐브 를 사용하여 사용한 큐브의 종류와 개수를 알 수 있습니다. \n자신의 API가 필요합니다. \n (ex : !큐브)"
    },
    {title: "제작자", content: "제작자 : Yeongbee \n\n Email : cyeongbee.gmail.com"}
];


function showContent(index, element) {
    const contentArea = document.getElementById('contentArea');
    contentArea.style.whiteSpace = "pre-line";
    const post = posts[index - 1];
    contentArea.innerHTML = `<h2>${post.title}</h2><p>${post.content}</p>`;

    document.querySelectorAll('#postList li').forEach(li => {
        li.classList.remove('selected');
    });

    element.classList.add('selected');
}

window.onload = function () {
    showContent(1, document.querySelector('#postList li')); // 첫 번째 포스트를 자동으로 보여줍니다.
};

const multiLineString = `이것은 여러 줄의 문자열입니다.
줄 바꿈이 그대로 유지됩니다.
    공백도 그대로 나타납니다.`;


function addNewPost() {
    const newPostContent = document.getElementById('newPost').value;
    if (newPostContent.trim() === '') {
        alert('글 내용을 입력해주세요.');
        return;
    }
    const newTitle = `새 글 ${posts.length + 1}`;
    posts.push({title: newTitle, content: newPostContent});
    const postList = document.getElementById('postList');
    const newLi = document.createElement('li');
    newLi.textContent = newTitle;
    newLi.onclick = () => showContent(posts.length);
    postList.appendChild(newLi);
    document.getElementById('newPost').value = '';
    alert('새 글이 추가되었습니다.');
}
