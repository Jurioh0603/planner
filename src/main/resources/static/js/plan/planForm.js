$(function() {
    $(".sortable").each(function() {
        var $sortable = $(this);
        // jQuery의 sortable을 사용하여 드래그 앤 드롭으로 정렬
        $sortable.sortable({
            // 정렬을 마치면 num-span, map 정보 update
            stop: function(event, ui) {
                updateNumbers($sortable);
                updateMap();
            }
        });

        // num-span 초기화
        updateNumbers($sortable);
    });

    // num-span 값 설정, index 값은 jQuery의 each 메서드에서 제공
    function updateNumbers($sortable) {
        $sortable.find("li").each(function(index) {
            $(this).find(".num-span").text(index + 1);
        });
    }

    // updateNumbers 함수를 전역 범위에 추가하여 다른 곳에서도 호출할 수 있도록 함
    window.updateNumbers = updateNumbers;
});

function getMapByDay(index) {
    sortableIdx = "sortable-" + index;
    updateMap();
}

function scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' }); // 'smooth' 옵션을 사용하여 부드럽게 스크롤됩니다.
}

function deleteItem(button) {
    // button의 부모 요소인 li를 찾아 삭제
    var li = button.closest('li');
    var $sortable = $(li).closest('.sortable');
    li.parentNode.removeChild(li);

    updateNumbers($sortable);
    updateMap();
}

function openPopup(day) {
    var url = "/plan/addAttr?keyword=강남+맛집&day=" + day;
    var width = 800;
    var height = 600;
    var left = (screen.width - width) / 2;
    var top = (screen.height - height) / 2;
    var pop = window.open(url, "장소 추가", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + ",resizable=no");
}

// 팝업창에서 입력한 데이터를 바탕으로 <li> 태그를 생성하여 해당 sortable-# 에 append
function addLocation(day, placeName, placeLatitude, placeLongitude, memo) {
    var dayIndex = day - 1;
    var $sortable = $('#sortable-' + dayIndex);

    var newLi = `
        <li class="ui-state-default" data-place-name="${placeName}" data-latitude="${placeLatitude}" data-longitude="${placeLongitude}" data-place-memo="${memo}">
            <div><img src="/images/hamburger.png" class="hamburgerMenu"/></div>
            <div class="centered-circle"><span class="num-span"></span></div>
            <div class="planDetail"><h4 class="placeName">${placeName}</h4><p class="m-0 memo">${memo}</p></div>
            <input type="button" class="deleteBtn" onclick="deleteItem(this)"/>
            <input type="button" class="modifyBtn" data-place-name="${placeName}" data-place-memo="${memo}"/>
        </li>
    `;

    $sortable.append(newLi);

    // num-span, map 정보 update
    updateNumbers($sortable);
    getMapByDay(dayIndex);
}

// "저장" 버튼 클릭 시 배열 생성 및 로깅
function saveScheduleArray() {
    const scheduleArray = [];
    var scheduleNo = 0;

    // 각 ul 태그를 순회하며 li 데이터를 추출하여 배열에 추가
    document.querySelectorAll('ul.sortable').forEach(ul => {
        scheduleNo = ul.getAttribute('data-schedule-no');
        const detailDay = ul.getAttribute('data-detail-day');

        ul.querySelectorAll('li.ui-state-default').forEach(li => {
            const placeProc = li.querySelector('.num-span').textContent;
            const placeName = li.getAttribute('data-place-name');
            const placeLatitude = li.getAttribute('data-latitude');
            const placeLongitude = li.getAttribute('data-longitude');
            const placeMemo = li.getAttribute('data-place-memo');

            scheduleArray.push({
                scheduleNo: scheduleNo,
                detailDay: detailDay,
                placeProc: placeProc,
                placeName: placeName,
                placeLatitude: placeLatitude,
                placeLongitude: placeLongitude,
                placeMemo: placeMemo
            });
        });
    });

    const data = {
        scheduleNo: scheduleNo,
        scheduleArray: scheduleArray
    };

    // AJAX 요청
    fetch('/plan/save', { // 요청 URL, 요청 옵션 객체
        method: 'POST', // HTTP POST
        headers: { // 전송 데이터 형식
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data) // 배열을 JSON으로 변환하여 포함
    })
    .then(data => {
        alert('저장 완료!');
    })
    .catch((error) => {
        alert('저장 실패');
    });
}

document.addEventListener('keydown', function(event) {
    // Ctrl + s 또는 Command + s 에 이벤트 추가
    if ((event.ctrlKey || event.metaKey) && event.key === 's') {
        event.preventDefault();  // default 액션 방지
        saveScheduleArray();  // 저장 버튼 클릭과 동일한 액션
    }
});

// 수정된 요소에 대한 이벤트 핸들러를 동적으로 추가
document.addEventListener('click', function(event) {
    // 클릭된 요소가 modifyBtn 클래스를 가진 요소인지 확인
    if (event.target.classList.contains('modifyBtn')) {
        const button = event.target;
        const liElement = button.closest('li');

        const name = button.getAttribute('data-place-name');
        const memo = button.getAttribute('data-place-memo');

        document.getElementById('editName').value = name;
        document.getElementById('editMemo').value = memo;

        // 수정 중인 <li> 요소를 모달에 저장
        document.getElementById('editModal').setAttribute('data-editing-element', JSON.stringify({
            name: name,
            memo: memo,
            element: liElement.outerHTML
        }));

        const modal = new bootstrap.Modal(document.getElementById('editModal'));
        modal.show();
    }
});

document.getElementById('edit').addEventListener('click', function() {
    const editName = document.getElementById('editName').value.trim();
    const editMemo = document.getElementById('editMemo').value.trim();

    if (!editName) {
        alert('장소 이름을 입력해주세요.');
        return;
    }

    if (!editMemo) {
        alert('메모를 입력해주세요.');
        return;
    }

    // 수정 중인 <li> 요소를 가져옴
    const editingElementStr = document.getElementById('editModal').getAttribute('data-editing-element');
    const editingElementData = JSON.parse(editingElementStr);
    const tempDiv = document.createElement('div');
    tempDiv.innerHTML = editingElementData.element;
    const liElement = tempDiv.firstChild;

    // <li> 요소의 속성 및 내용을 업데이트
    liElement.setAttribute('data-place-name', editName);
    liElement.setAttribute('data-place-memo', editMemo);

    liElement.querySelector('.placeName').textContent = editName;
    liElement.querySelector('.memo').textContent = editMemo;

    const modifyBtn = liElement.querySelector('.modifyBtn');
    modifyBtn.setAttribute('data-place-name', editName);
    modifyBtn.setAttribute('data-place-memo', editMemo);

    // 실제 DOM에서 수정된 <li> 요소를 업데이트
    const originalLiElement = document.querySelector(`li[data-place-name='${editingElementData.name}'][data-place-memo='${editingElementData.memo}']`);
    if (originalLiElement) {
        originalLiElement.replaceWith(liElement);
    }

    // 모달 닫기
    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
    modal.hide();
});

// =======================================================

var map = null;
var sortableIdx = "sortable-0";
var markers = []; // 마커를 담을 배열입니다
var linePath = null; // 마커 연결선
var points = []; // 지도 범위를 재설정하기 위한 배열

script.onload = function() {
    loadMap();
};

function loadMap() {
    kakao.maps.load(function() {

        map = drawMap(); //지도 생성

        var positions = getPositions(sortableIdx); //장소 정보 가져오기

        if(positions.length > 0) {
            setBounds();
        }

        displayPlaces(positions); //마커 생성 및 그리기
        displayLines(positions); //마커 연결선 생성 및 그리기
    });
}

function updateMap() {

    removeMarker(); //마커와 연결선 제거

    var positions = getPositions(sortableIdx); //장소 정보 가져오기

    if(positions.length > 0) {
        setBounds();
    }

    displayPlaces(positions); //마커 생성 및 그리기
    displayLines(positions); //마커 연결선 생성 및 그리기
}

function setBounds() {
    var bounds = new kakao.maps.LatLngBounds();
    for(var i = 0; i < points.length; i++) {
        bounds.extend(points[i]);
    }
    map.setBounds(bounds);
}

function drawMap() {
    var container = document.getElementById('map'); //지도를 표시할 div
    var options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표
        level: 3 //지도의 확대 레벨
    };

    return new kakao.maps.Map(container, options);
}

function getPositions(sortableIdx) {
    var positions = [];
    points = [];

    $("#" + sortableIdx + " li").each(function() {
        var placeName = $(this).data("place-name");
        var latitude = parseFloat($(this).data("latitude"));
        var longitude = parseFloat($(this).data("longitude"));

        if (placeName && !isNaN(latitude) && !isNaN(longitude)) {
            positions.push({
                title: placeName,
                latlng: new kakao.maps.LatLng(latitude, longitude)
            });
            points.push(new kakao.maps.LatLng(latitude, longitude));
        }
    });

    return positions;
}

function displayPlaces(positions) {
    for(var i = 0; i < positions.length; i++) {
        var placePosition = positions[i].latlng;
        var placeTitle = positions[i].title;
        addMarker(placePosition, i, placeTitle);
    }
}

function addMarker(position, idx, title) {
    // 마커 이미지의 이미지 주소입니다
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: position, // 마커를 표시할 위치
        title: title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage // 마커 이미지
    });

    marker.setMap(map);
    markers.push(marker);

    return marker;
}

function displayLines(positions) {
        var polyline = [];
        for (var i = 0; i < positions.length; i++) {
            polyline.push(positions[i].latlng);
            if(polyline.length == 15) {
                break;
            }
        }

        //마커 연결
        drawLine(map, polyline);
}

function drawLine(map, polyline) {
        linePath = new kakao.maps.Polyline({
            path: polyline,       // 선을 구성하는 좌표배열입니다
            strokeWeight: 3,      // 선의 두께입니다
            strokeColor: 'black', // 선의 색깔입니다
            strokeOpacity: 0.7,   // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid'  // 선의 스타일입니다
        });

        linePath.setMap(map);
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
    linePath.setMap(null);

    markers = [];
    linePath = null;
}