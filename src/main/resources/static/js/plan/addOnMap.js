var marker = null;

//default
script.onload = function() {
    kakao.maps.load(function() {
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        var imageSrc = '/images/marker.png', // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(55, 55), // 마커이미지의 크기입니다
        imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
        markerPosition = new kakao.maps.LatLng(33.450701, 126.570667); // 마커가 표시될 위치입니다

        // 지도를 클릭한 위치에 표출할 마커입니다
        marker = new kakao.maps.Marker({
            // 지도 중심좌표에 마커를 생성합니다
            position: map.getCenter(),
            image: markerImage // 마커이미지 설정
        });

        // 지도에 마커를 표시합니다
        marker.setMap(map);

        // 지도에 클릭 이벤트를 등록합니다
        // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

            // 클릭한 위도, 경도 정보를 가져옵니다
            var latlng = mouseEvent.latLng;

            // 마커 위치를 클릭한 위치로 옮깁니다
            marker.setPosition(latlng);
        });
    });
};

// 추가 버튼에 클릭 이벤트 추가
var addButton = document.getElementById('select');
addButton.addEventListener('click', function() {
    var latlng = marker.getPosition();
    var lat = latlng.getLat();
    var lng = latlng.getLng();

    do {
        var placeName = prompt("장소 이름").trim();
        if(placeName == '') {
            alert('이름을 입력해주세요.');
        } else if(!isValidByteLength(placeName, 100)) {
            alert('이름은 최대 100byte를 초과할 수 없습니다. 다시 입력해주세요.')
        }
    } while (placeName == '' || !isValidByteLength(placeName, 100));

    do {
        var memo = prompt("장소 메모").trim();
        if(memo == '') {
            alert('메모를 입력해주세요.');
        } else if(!isValidByteLength(memo, 300)) {
            alert('메모는 최대 300byte를 초과할 수 없습니다. 다시 입력해주세요.')
        }
    } while (memo == '' || !isValidByteLength(memo, 300));

    sendSchedule(day, placeName, lat, lng, memo);
});

function sendSchedule(day, placeName, placeLatitude, placeLongitude, memo) {
    // 부모 창의 addLocation 함수 호출
    window.opener.addLocation(day, placeName, placeLatitude, placeLongitude, memo);

    // 팝업 창 닫기
    window.close();
}

function isValidByteLength(str, maxByte) {
    var str_len = str.length;
    var rbyte = 0;
    var one_char = "";

    for (var i = 0; i < str_len; i++) {
        one_char = str.charAt(i);
        if (escape(one_char).length > 4) {
            rbyte += 3;
        } else {
            rbyte++;
        }

        if (rbyte > maxByte) {
            return false;
        }
    }

    return true;
}