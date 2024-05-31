const API_KEY = 'cd4e311164901e06e47b3d8f546073fe';
const iconSection = document.querySelector('.icon');
const tempSection = document.querySelector('.temp');
const placeSection = document.querySelector('.place');
const descSection = document.querySelector('.desc');

// DOM 요소가 제대로 선택되었는지 확인합니다.
if (!iconSection || !tempSection || !placeSection || !descSection) {
  console.error('One or more DOM elements are not found.');
  alert('필요한 DOM 요소를 찾을 수 없습니다. HTML 구조를 확인하세요.');
}

document.addEventListener('DOMContentLoaded', () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(success, fail);
  } else {
    alert("Geolocation is not supported by this browser.");
  }
});

const success = (position) => {
  const latitude = position.coords.latitude;
  const longitude = position.coords.longitude;
  getWeather(latitude, longitude);
}

const fail = (error) => {
  let errorMessage;
  switch (error.code) {
    case error.PERMISSION_DENIED:
      errorMessage = "User denied the request for Geolocation.";
      break;
    case error.POSITION_UNAVAILABLE:
      errorMessage = "Location information is unavailable.";
      break;
    case error.TIMEOUT:
      errorMessage = "The request to get user location timed out.";
      break;
    case error.UNKNOWN_ERROR:
      errorMessage = "An unknown error occurred.";
      break;
  }
  alert(errorMessage);
}

const getWeather = (lat, lon) => {
  fetch(
    `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
  )
  .then((response) => {
    if (!response.ok) {
      throw new Error('Network response was not ok ' + response.statusText);
    }
    return response.json();
  })
  .then((json) => {
    const temperature = json.main.temp;
    const place = json.name;
    const description = json.weather[0].description;
    const icon = json.weather[0].icon;
    const iconURL = `http://openweathermap.org/img/wn/${icon}@2x.png`;

    tempSection.innerText = `${temperature} °C`;
    placeSection.innerText = place;
    descSection.innerText = description;
    if (iconSection.tagName === 'IMG') {
      iconSection.setAttribute('src', iconURL);
    } else {
      iconSection.style.backgroundImage = `url(${iconURL})`;
    }
  })
  .catch((error) => {
    alert(`Error: ${error.message}`);
  });
}