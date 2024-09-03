var marker = null;

//default
script.onload = function() {
    updateMap('제주특별자치도 제주시 첨단로 242');
}

function updateMap(address) {
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

        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(address, function(result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 마커를 생성합니다
                marker = new kakao.maps.Marker({
                    position: coords,
                    image: markerImage // 마커이미지 설정
                });

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(map);

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            }
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

window.onload = function(){
    document.getElementById("address_kakao").addEventListener("click", function(){ //주소 입력칸을 클릭하면
        //카카오 지도 발생
        new daum.Postcode({
            oncomplete: function(data) { //선택시 입력값 세팅
                document.getElementById("search").value = data.address; //주소 넣기
                updateMap(data.address);
            }
        }).open();
    });
}