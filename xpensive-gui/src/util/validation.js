export const validateEmail = (email) => {
    if(email.trim()){
        const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRe.test(email);
    }
}