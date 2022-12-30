package com.adrianbutler.madcloud.utils.api;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GraphQLManager
{
	private static final String TAG = "GraphQLManager";

	public static String createUser(String name) // Creates a user and returns the users id
	{
		if (userExists(name))
		{
			Log.w(TAG, "Failed to create new user because username is already taken");
			return null;
		}

		User newUser = User.builder()
				.username(name)
				.highScore(0)
				.build();

		Amplify.API.mutate(
				ModelMutation.create(newUser),
				success ->
						Log.i(TAG, "Successfully created User with name: " + name),
				failure ->
						Log.w(TAG, "Failed to create User with name: " + name)
		);

		return newUser.getId();
	}

	private static boolean userExists(String name)
	{
		AtomicBoolean userExists = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);

		// Check if user exists
		Amplify.API.query(ModelQuery.list(User.class, User.USERNAME.eq(name)),
				success ->
				{
					if (success.getData().iterator().hasNext()) // if user with name exists
					{
						userExists.set(true);
					}
					latch.countDown();
				},
				failure ->
				{
					Log.e(TAG, "Failed to query database: " + failure);
					latch.countDown();
				});

		try
		{
			latch.await();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return userExists.get();
	}

	public static User findUserById(String id)
	{
		AtomicReference<User> foundUser = new AtomicReference<>(); // atomic is used to change the variable from a lambda
		CountDownLatch latch = new CountDownLatch(1); // latch is necessary to make api query synchronous

		Amplify.API.query(ModelQuery.get(User.class, id),
				success ->
				{
					Log.i(TAG, "Found user with id: " + id);
					foundUser.set(success.getData());

					latch.countDown();
				},
				failure ->
				{
					Log.i(TAG, "Failed to query by id: " + failure);
					latch.countDown();
				});

		try
		{
			latch.await();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return foundUser.get();
	}

	public static List<User> getUsersByDescendingScore()
	{
		AtomicReference<List<User>> userListReference = new AtomicReference<>();
		CountDownLatch latch = new CountDownLatch(1);


		Amplify.API.query(ModelQuery.list(User.class),
				success ->
				{
					Log.i(TAG, "Successfully queried sorted users");

					List<User> userList = new ArrayList<>();
					success.getData().iterator().forEachRemaining(userList::add); // add all of the results into the list

					userList.sort(Comparator.comparing(User::getHighScore));
					Collections.reverse(userList);

					userListReference.set(userList);

					latch.countDown();
				},
				failure ->
				{
					Log.w(TAG, "Failed to query users: " + failure);
					latch.countDown();
				});

		try
		{
			latch.await();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return userListReference.get();
	}

	public static void updateUsersHighScore(String id, int newHighScore)
	{
		User user = findUserById(id);

		user = user.copyOfBuilder().highScore(newHighScore).build();

		Amplify.API.mutate(ModelMutation.update(user),
				success ->
				{
					Log.i(TAG, "Successfully updated high score");
				},
				failure ->
				{
					Log.e(TAG, "Failed to update high score");
				});
	}
}
