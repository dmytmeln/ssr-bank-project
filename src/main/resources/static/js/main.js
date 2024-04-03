const formContainer = document.querySelector(".form_container");

formContainer.addEventListener("click", (e) => {
    const target = e.target;

    if (target.id === "signup") {
        e.preventDefault();
        formContainer.classList.add("active");
    } else if (target.id === "login") {
        e.preventDefault();
        formContainer.classList.remove("active");
    } else if (target.id === "login_hide") {
        e.preventDefault();
        togglePasswordVisibility("#login_password");
    } else if (target.id === "signup_hide") {
        e.preventDefault();
        togglePasswordVisibility("#signup_password");
    }
});

function togglePasswordVisibility(passwordInputId) {
    const passwordInput = document.querySelector(passwordInputId);
    passwordInput.type = passwordInput.type === "password" ? "text" : "password";
}