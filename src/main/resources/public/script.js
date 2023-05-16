function increaseValue(max) {
    var value = parseInt(document.getElementById('number').value, 10);
    value = isNaN(value) ? 0 : value;
    if(value<max){
        value++;
    }
    document.getElementById('number').value = value;
}

function decreaseValue() {
    var value = parseInt(document.getElementById('number').value, 10);
    value = isNaN(value) ? 0 : value;
    value < 1 ? value = 1 : '';
    value--;
    document.getElementById('number').value = value;
}

function hideDiv() {
    var x = document.getElementById("myDIV");
    var y = document.getElementById("adresFormButton");
    if (x.style.display === "none") {
        x.style.display = "block";
        y.style.display="block";
    } else {
        x.style.display = "none";
        y.style.display="none";
    }
}