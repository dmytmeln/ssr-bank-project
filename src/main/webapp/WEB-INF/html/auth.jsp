<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String errorMsg = (String) request.getAttribute("errorMsg");%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <!-- Unicons -->
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
        <title>Authorization</title>
        <style>
            <%@include file="../css/style.css"%>
        </style>
    </head>

    <body>
        <section class="home">
            <h1 class="error_msg"><%=errorMsg == null ? "" : errorMsg%></h1>
            <div class="form_container">
                <a href="/"><i class="uil uil-times form_close"></i></a>
                <!-- Login From -->
                <div class="form login_form">
                    <form method="post" action="/auth/login">
                        <h2>Login</h2>

                        <div class="input_box">
                            <input type="email" name="email" placeholder="Enter your email" required />
                            <i class="uil uil-envelope-alt email"></i>
                        </div>

                        <div class="input_box">
                            <input type="password" name="password" id="login_password" placeholder="Enter your password" required />
                            <i class="uil uil-lock password"></i>
                            <i class="uil uil-eye-slash pw_hide" id="login_hide"></i>
                        </div>

                        <div class="input_box">
                            <input type="tel" name="phone_number" placeholder="Enter your phone number" required />
                        </div>

                        <div class="option_field">
                            <span class="checkbox">
                            <input type="checkbox" id="check" />
                            <label for="check">Remember me</label>
                            </span>
                            <a href="#" class="forgot_pw">Forgot password?</a>
                        </div>

                        <button class="button">Login Now</button>

                        <div class="login_signup">Don't have an account? <a href="#" id="signup">Signup</a></div>
                    </form>
                </div>

                <div class="form signup_form">
                    <form method="post" action="/auth/signup">
                        <h2>Signup</h2>

                        <div class="input_box">
                            <input type="email" name="email" placeholder="Enter your email" required />
                            <i class="uil uil-envelope-alt email"></i>
                        </div>

                        <div class="input_box">
                            <input type="text" name="firstname" placeholder="Enter your firstname" required />
                        </div>

                        <div class="input_box">
                            <input type="text" name="lastname" placeholder="Enter your lastname" required />
                        </div>

                        <div class="input_box">
                            <input type="password" name="password" id="signup_password" placeholder="Create password" required />
                            <i class="uil uil-lock password"></i>
                            <i class="uil uil-eye-slash pw_hide" id="signup_hide"></i>
                        </div>

                        <div class="input_box">
                            <input type="tel" name="phone_number" placeholder="Enter your phone number" required />
                        </div>

                        <button class="button">Signup Now</button>

                        <div class="login_signup">Already have an account? <a href="#" id="login">Login</a></div>
                    </form>
                </div>
            </div>
        </section>

        <script>
            <%@include file="../js/main.js"%>
        </script>
    </body>

</html>