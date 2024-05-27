'use strict';

var roomSelectionPage = document.querySelector('#room-selection-page');
var usernamePage = document.querySelector('#username-page'); //사용자가 자신의 이름을 입력
var chatPage = document.querySelector('#chat-page'); //실제 채팅 메세지 표시
var roomForm = document.querySelector('#roomForm');
var usernameForm = document.querySelector('#usernameForm'); //이름입력폼
var messageForm = document.querySelector('#messageForm'); //메세지 입력폼
var messageInput = document.querySelector('#message'); //메세지입력 필드
var messageArea = document.querySelector('#messageArea'); //메세지 표시되는 영역
var connectingElement = document.querySelector('.connecting'); //연결상태 나타내는 요소

var stompClient = null; // 클라이언트의 인스턴스 저장할 변수, 웹소켓 연결을 위해 사용됨
var username = null; //사용자의 이름을 저장할 변수 선언
var roomId = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connectToRoom(event) {
    event.preventDefault();

    roomId = document.querySelector('#roomId').value.trim();
    if (roomId) {
        roomSelectionPage.classList.add('hidden');
        usernamePage.classList.remove('hidden');
    }
}

// 이 함수는 사용자가 이름을 입력하고 서버와의 웹소켓 연결을 설정하여 채팅 페이지로 전환하는 역할을 함
function connect(event) { // 사용자가 채팅에 연결할 때 호출되는 함수
    event.preventDefault();

    username = document.querySelector('#name').value.trim(); // #name 아이디를 가진 입력 필드에서 사용자가 입력한 이름 가져옴

    if(username && roomId) { // 사용자가 이름을 입력했는지 확인
        usernamePage.classList.add('hidden'); // userNamePage 요소에 hidden클래스 추가해 이름입력 창 숨김
        chatPage.classList.remove('hidden'); // chatPage요소에 hidden클래스 제거해 채팅페이지 표시

        var socket = new SockJS('/ws'); // 새로운 SockJS 객체 생성하여 서버의 /ws엔드포인트에 연결, 웹소켓 연결을 처리
        stompClient = Stomp.over(socket); // SocketJS 객체를 사용해 Stomp 클라이언트 생성, stompClient 변수에 저장, stomp는 메시지 브로커와 통신하기 위한 프로토콜

        stompClient.connect({}, onConnected, onError); // stomp 클라이언트 활용해 연결 시도, 성공시 onConnected, 오류 발생시 error 콜백 함수 호출
    }
    event.preventDefault(); // 폼의 기본 제출 동작 막기, 페이지가 새로고침되는것 방지
}

// 이 함수는 채팅 서버에 연결 되었을 때 수행되는 작업들을 정의 - 공개 주제를 구독하고, 사용자 이름과 참여유형을 서버에 알라고, 연결 상태를 나타내는 요소를 숨김
function onConnected() {
    console.log('Connected to WebSocket server');
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/' + roomId, function (message) {
            console.log('Received message:', message.body);
            onMessageReceived(message);
        }); // 클라이언트를 사용하여 '/topic/public'이라는 주제를 구독, 메시지가 수신되면 onMessageReceived 함수 호출

    // Tell your username to the server
    stompClient.send("/app/chat.addUser" + roomId, // 서버로 메세지를 보내는 기능 '/app/chat.addUser경로로 빈 헤더 {} 와 함께 메시지 본문을 JSON 문자열로 전송
        {},
        JSON.stringify({sender: username, type: 'JOIN', roomId: roomId}) // 본문에는 sender, type 이 포함
    )

    connectingElement.classList.add('hidden'); // connectingElement라는 요소에 hidden 클래스 추가해 해당 요소 숨김, 일반적으로 연결상태를 사용자에게 보이지 않게 하기 위함
}

//이 함수는 웹소켓과 서버 연결이 실패했을 때 실행됨
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!'; // 웹소켓과 서버 연결 실패 시 사용자에게 알리는 메세지
    connectingElement.style.color = 'red'; // 텍스트 색
}

// 이 함수는 사용자가 메시지를 입력하고 보내기 버튼을 클릭하거나, 엔터키를 눌렀을 때 실행 됨. 사용자가 입력한 메시지를 서버에 보내고 입력 필드를 비우는 기능 수행
function sendMessage(event) {
    event.preventDefault();

    var messageContent = messageInput.value.trim(); // messageInput에서 입력한 사용자의 메시지 값을 가져와 앞뒤공백 제거 후 그 결과를 messageContent에 저장한다.
    if(messageContent && stompClient) { // messageContent가 비어있지 않고, stompClient 객체가 존재하는지 확인
        var chatMessage = { // 모낼 메시지의 정보를 포함하는 객체 생성, 메세지를 보내는 사람(sender), 메세지 내용(content), 메세지 유형(type 중에서 CHAT)을 포함,
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            roomId: roomId
        };
        console.log('Sending message:', chatMessage);
        stompClient.send("/app/chat.sendMessage" + roomId, {}, JSON.stringify(chatMessage)); // stompClient 를 사용해 서버에 메시지를 보내는 명령 실행, 메시지는 '/app/chat.sendMessage' 경로로 보내지고 메세지 헤더는 빈 객체로, 메세지 본문은 chatMessage 객체를 JSON 문자열로 변환한것
        messageInput.value = ''; // 메세지를 보낸 후 입력 필드를 비움
    }
}

// 이 함수는 들어오는 메시지를 처리함, 가입, 퇴장, 일반 채팅 메시지를 구별하고 DOM을 그에 맞게 업데이트하며 메시지 영역이 스크롤되어 최신메시지가 표시되도록 함
function onMessageReceived(payload) {
    console.log('Message received:', payload.body);
    var message = JSON.parse(payload.body); // payload.body에 있는 JSON 문자열을 파싱하여 자바스크립트 객체로 만들고 그 객체를 변수에 할당

    var messageElement = document.createElement('li'); //새로운 <li> DOM요소를 생성하고 변수에 할당

    if(message.type === 'JOIN') { // message 객체의 type 속성이 JOIN 인지 확인
        messageElement.classList.add('event-message'); // 메시지 유형이 JOIN이라면 messageElement에 event-message 클래스를 추가(말풍선 부분)
        message.content = message.sender + ' 님이 들어왔습니다.'; // message객체의 content속성을 보내는 사람이 참여했다는 내용으로 업데이트
    } else if (message.type === 'LEAVE') { // 메시지 객체의 type 속성이 LEAVE인지 확인
        messageElement.classList.add('event-message'); // 메세지 유형이 LEAVE라면 messageElement에 event-message 클래스 추가
        message.content = message.sender + ' 님이 나갔습니다'; // message객체의 content속성을 보내는 사람이 나갔다는 내용으로 업데이트
    } else {
        messageElement.classList.add('chat-message'); // message 객채의 type 속성이 CHAT 이라면 messageElement에 chat-message 클래스 추가

        var avatarElement = document.createElement('i'); // 새로운 <i> DOM 요소 생성, 변수에 할당
        var avatarText = document.createTextNode(message.sender[0]); // 보내는 사람의 첫 글자를 포함하는 텍스트 노드 생성, 변수에 할당
        avatarElement.appendChild(avatarText); // avatarText 텍스트 노드를 avatarElement에 추가
        avatarElement.style['background-color'] = getAvatarColor(message.sender); //보내는 사람의 이름을 기반으로 생성된 색상으로 avatarElement의 배경색 설정

        messageElement.appendChild(avatarElement); // avatarElement를 messageElement에 추가

        var usernameElement = document.createElement('span'); //새로운 <span> DOM요소를 생성, 변수에 할당
        var usernameText = document.createTextNode(message.sender); //보내는 사람의 이름을 포함하는 텍스트 노드를 생성, 변수에 할당
        usernameElement.appendChild(usernameText); // usernameText 텍스트 노드를 usernameElement에 추가
        messageElement.appendChild(usernameElement); // usernameElement를 messageElement에 추가
    }

    var textElement = document.createElement('p'); // 새로운 <p> DOM요소를 생성, 변수에 할당
    var messageText = document.createTextNode(message.content); // 메시지 내용을 포함하는 텍스트 노드 생성, 변수에 할당
    textElement.appendChild(messageText); // messageText 텍스트 노드를 textElement에 추가

    messageElement.appendChild(textElement); // textElement를 messageElement에 추가

    messageArea.appendChild(messageElement); // messageElement를 messageArea에 추가, messageArea는 메시지가 표시되는 UI영역
    messageArea.scrollTop = messageArea.scrollHeight; // messageArea를 스크롤 하여 최신 메시지가 보이도록 함
}

// 이 함수는 주어진 messageSender 문자열에 따라 아바타의 색상을 결정하는 역할을 함. 해시값을 계산해 color배열에서 적절한 색상을 선택
function getAvatarColor(messageSender) { // messageSender를 매개변수로 받아 아바타 색상을 반환하는 함수 정의
    var hash = 0; // hash라는 변수를 초기화
    for (var i = 0; i < messageSender.length; i++) { // messageSender 문자열의 각 문자를 순회하기 위해 for루프 시작
        hash = 31 * hash + messageSender.charCodeAt(i); // messageSender 문자열의 각 유니코드 값을 가져와 hash값 갱신, 해시값 계산을 위해 31 곱하고 현재 문자의 유니코드 값 더함
    }
    var index = Math.abs(hash % colors.length); // color 배열의 길이로 해시 값을 나눈 나머지를 구하고 절대값을 index변수에 할당, 이는 색상배열에서 사용할 인덱스를 결정
    return colors[index]; // color배열의 index위치에 있는 색상 반환
}

roomForm.addEventListener('submit', connectToRoom, true);
usernameForm.addEventListener('submit', connect, true); // usernameForm이라는 엘리먼트에 submit 이벤트 리스너 추가, 이 이벤트가 발생하면 connect함수 호출, 이 때 true는 이벤트 리스너를 캡처단계에서 활성화하겠다는 의미
messageForm.addEventListener('submit', sendMessage, true); // messageForm이라는 엘리먼트에 submit 이벤트 리스너 추가, 이 이벤트가 발생하면 sendMessage 함수가 호출, 이 때 true는 이벤트 리스너를 캡처단계에서 활성화하겠다는 의미