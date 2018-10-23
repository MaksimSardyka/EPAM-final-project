function checkRepeatPasswordMatch(theForm) {
    if (theForm.password.value != theForm.repeat_password.value)
    {
        alert('Those passwords don\'t match!');
        return false;
    } else {
        return true;
    }
}