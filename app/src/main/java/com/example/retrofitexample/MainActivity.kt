package com.example.retrofitexample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchUserDetails(1)
        updateUserDetails(1, User(1, "New name", "newname", "newname@example.com"))
        createUser(User(3, "sdf name", "asfhu", "newname@example.com"))
        fetchUsers(5)
    }

    private fun fetchUserDetails(userId: Int) {
        val call = RetrofitClient.instance.getUserDetails(userId)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    user?.let {
                        Log.d("MainActivity", "Username: ${it.name}")
                        Toast.makeText(
                            this@MainActivity,
                            "UserName: ${it.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to retrieve user", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateUserDetails(userId: Int, user: User) {
        val call = RetrofitClient.instance.updateUserDetails(userId, user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "User updated: ${response.body()?.name}")
                } else {
                    Toast.makeText(this@MainActivity, "Failed to update user", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun createUser(user: User) {
        val call = RetrofitClient.instance.createUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "User created: ${response.body()?.name}")
                } else {
                    Toast.makeText(this@MainActivity, "Failed to create user", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun fetchUsers(page: Int) {
        val call = RetrofitClient.instance.getUsers(page)

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (user in it) {
                            Log.d("MainActivity", "User: ${user.name}")
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch users", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })


    }
}