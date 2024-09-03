document.addEventListener('DOMContentLoaded', function() {
    // 모든 carousel-item 요소들을 선택
    var carouselItems = document.getElementsByClassName('carousel-item');
    var carouselBtns = document.getElementsByClassName('carouselBtn');

    // 첫 번째 carousel-item 요소가 존재하는지 확인
    if (carouselItems.length > 0) {
        // 첫 번째 carousel-item 요소에 active 클래스 추가
        carouselItems[0].classList.add('active');
    }

    if (carouselBtns.length > 0) {
        carouselBtns[0].classList.add('active');
    }

    var deleteBtn = document.getElementById("deleteBtn");

    // 작성자가 아닌 경우 삭제 버튼이 존재하지 않는다. 따라서 존재 여부를 파악하여 이벤트 리스너를 추가한다.
    if (deleteBtn) {
        deleteBtn.addEventListener("click", function () {
            const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
            modal.show();
        });
    }
});

$(document).ready(function() {
    $(".day").each(function() {
        var $day = $(this);

        updateNumbers($day);
    });

    // num-span 값 설정
    function updateNumbers($day) {
        $day.find("li").each(function(index) {
            $(this).find(".num-span").text(index + 1);
        });
    }
});

// 'day #' 클릭시 해당 날짜 데이터로 지도 update
function getMapByDay(index) {
    dayIdx = "day-" + index;
    updateMap();
}

// 'day #' 클릭시 지도 위치로 smooth하게 스크롤
function scrollToElement(elementId) {
    var element = document.getElementById(elementId);
    if (element) {
        var elementPosition = element.getBoundingClientRect().top + window.scrollY;
        window.scrollTo({ top: elementPosition, behavior: 'smooth' });
    }
}

document.querySelectorAll('.scheduleSaveBtn').forEach(function(element) {
    element.addEventListener('click', function(event) {
        var scheduleNo = this.getAttribute('scheduleNo');
        var data = {
            scheduleNo: scheduleNo
        };

        // AJAX 요청
        fetch('/tlog/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(data => {
            console.log('Success:', data);
            alert('저장 완료!');
        })
        .catch((error) => {
            alert('저장 실패');
        });
    });
});

// ================================================

var map = null;
var dayIdx = "day-0";
var markers = []; // 마커를 담을 배열입니다
var linePath = null; // 마커 연결선
var points = []; // 지도 범위를 재설정하기 위한 배열

script.onload = function() {
    loadMap();
};

function loadMap() {
    kakao.maps.load(function() {

        map = drawMap(); //지도 생성

        var positions = getPositions(dayIdx); //장소 정보 가져오기

        if(positions.length > 0) {
            setBounds();
        }

        displayPlaces(positions); //마커 생성 및 그리기
        displayLines(positions); //마커 연결선 생성 및 그리기
    });
}

function updateMap() {

    removeMarker(); //마커와 연결선 제거

    var positions = getPositions(dayIdx); //장소 정보 가져오기

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

function getPositions(dayIdx) {
    var positions = [];
    points = [];

    $("#" + dayIdx + " li").each(function() {
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