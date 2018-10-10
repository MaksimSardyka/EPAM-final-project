//$(document).ready(function() {
//	var login = document.getElementById('username');
//	var input = document.getElementById('password');
//	var form  = document.getElementById('form');
//	
//	var elem           = document.createElement('div');
//    elem.id            = 'notify';
//    elem.style.display = 'none';
//    form.appendChild(elem);
//    
//    login.addEventListener('invalid', function(event){
//        event.preventDefault();
//        if ( ! event.target.validity.valid ) {
//            elem.textContent   = 'Username: 6-40 Latin letters/digits/spaces';
//            elem.className     = 'error';
//            elem.style.display = 'block';
//     
//            login.className    = 'invalid';
//        }
//    });
//    
//    //call on input change
//    login.addEventListener('login', function(event){
//        if ( 'block' === elem.style.display ) {
//            login.className = '';
//            elem.style.display = 'none';
//        }
//    });
//});