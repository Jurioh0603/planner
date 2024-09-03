function fnChkByte(obj, maxByte) {
    var str = obj.value;
    var str_len = str.length;

    var rbyte = 0;
    var rlen = 0;
    var one_char = "";
    var str2 = "";

    for(var i = 0; i < str_len; i++) {
        one_char = str.charAt(i);
        if (escape(one_char).length > 4) {
            rbyte += 3;
        } else {
            rbyte++;
        }

        if(rbyte <= maxByte) {
            rlen = i + 1;
        }
    }

    if(rbyte > maxByte) {
        alert("최대 " + maxByte + "byte를 초과할 수 없습니다.")
        str2 = str.substr(0, rlen);
        obj.value = str2;
        return;
    }
}