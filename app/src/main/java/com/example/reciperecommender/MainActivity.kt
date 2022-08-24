package com.example.reciperecommender

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.reciperecommender.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    //Check if user is logged in
    private var loggedIn: Boolean = false

    //Get DB Helper
    private val DB = DBHelper(this)

    //Navigation Values
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val accountFragment = AccountFragment()
    private val signupFragment = SignupFragment()
    private val accountLoggedInFragment = AccountFragment_LoggedIn()
    private val savedRecipesFragment = SavedRecipesFragment()
    private val savedRecipesNotLoggedIn = SavedRecipesNotLoggedIn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bottom Navigation Handler
        makeCurrentFragment(homeFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_collections -> if (loggedIn) makeCurrentFragment(savedRecipesFragment) else makeCurrentFragment(savedRecipesNotLoggedIn)
                R.id.ic_account -> if (loggedIn) makeCurrentFragment(accountLoggedInFragment) else makeCurrentFragment(accountFragment)
            }
            true
        }
    }

    //Navigation Handling
    fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    internal fun login()
    {
        val usernameLogin = findViewById<TextInputEditText>(R.id.usernamelogin)
        val passwordLogin = findViewById<TextInputEditText>(R.id.passwordlogin)
        val user: String = usernameLogin.text.toString()
        val pass: String = passwordLogin.text.toString()

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this@MainActivity, "All Fields Required", Toast.LENGTH_SHORT).show()
        } else {
            if (DB.checkUsernameAndPassword(user, pass)) {
                Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                loggedIn = true
                makeCurrentFragment(accountLoggedInFragment)
            } else {
                Toast.makeText(this@MainActivity, "Incorrect Login Details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal fun register()
    {
        val username = findViewById<TextInputEditText>(R.id.username)
        val password = findViewById<TextInputEditText>(R.id.password)
        val repassword = findViewById<TextInputEditText>(R.id.repassword)

        val user: String = username.text.toString()
        val pass: String = password.text.toString()
        val repass: String = repassword.text.toString()

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this@MainActivity, "All Fields Required", Toast.LENGTH_SHORT).show()
        } else {
            if (pass == repass) {
                if(DB.checkUsername(user) == false) {
                    if (passwordValidator(pass)) {
                        if (DB.insertData(user, pass) == true) {
                            Toast.makeText(
                                this@MainActivity,
                                "Registered Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            loggedIn = true
                            makeCurrentFragment(accountLoggedInFragment)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Registration Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Password does not meet requirements", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "User already exists", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun passwordValidator(str: String): Boolean
    {
        var ch: Char
        var capitalFlag = false
        var lowerCaseFlag = false
        var numberFlag = false
        for (i in 0 until str.length) {
            ch = str[i]
            if (Character.isDigit(ch)) {
                numberFlag = true
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true
            }
            if (numberFlag && capitalFlag && lowerCaseFlag && str.length >= 8) return true
        }
        return false
    }

    internal fun signUpLink() {
        makeCurrentFragment(signupFragment)
    }

    internal fun loginLink() {
        makeCurrentFragment(accountFragment)
    }

    internal fun logout() {
        loggedIn = false
        makeCurrentFragment(accountFragment)
    }

    internal fun searchLink() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setSelectedItemId(R.id.ic_search)
        makeCurrentFragment(searchFragment)
    }

    internal fun logInLink() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setSelectedItemId(R.id.ic_account)
        makeCurrentFragment(accountFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // This was causing me issues
    // Set as a toast widget for demonstrative purposes
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.light) {
            goLight()
        }
        if (id == R.id.dark) {
            goDark()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goLight() {
        Toast.makeText(this, "Light mode triggered", Toast.LENGTH_SHORT).show()
    }

    private fun goDark() {
        Toast.makeText(this, "Dark mode triggered", Toast.LENGTH_SHORT).show()
    }

    public override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    public override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    public override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    public override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy")
    }
}

