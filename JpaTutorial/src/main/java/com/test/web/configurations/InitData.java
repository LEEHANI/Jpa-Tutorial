package com.test.web.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.test.web.entities.Address;
import com.test.web.entities.Comment;
import com.test.web.entities.Membership;
import com.test.web.entities.Phone;
import com.test.web.entities.Post;
import com.test.web.entities.PostTag;
import com.test.web.entities.Tag;
import com.test.web.entities.User;
import com.test.web.enums.Gender;
import com.test.web.repositories.UserRepository;

@Component
public class InitData implements CommandLineRunner
{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception
	{
		System.out.println("** Init Data Start **");
		
		List<User> users = new ArrayList<>();
		
		//User
		User user1 = User.builder().userId("user1").password("pass1").gender(Gender.FEMALE).build();
		User user2 = User.builder().userId("user2").password("pass2").build();
		User user3 = User.builder().userId("user3").password("pass3").build();
		
		//Membership 1:1 단방향 바인딩
		user1.setMembership(Membership.builder().point(1).build());
		user2.setMembership(Membership.builder().point(2).build());
		user3.setMembership(Membership.builder().point(3).build());
		
		//Address
		//1:1 양방향 바인딩
		user1.bind(Address.builder().city("서울").street("역삼동").build());
		user2.bind(Address.builder().city("서울").street("언주동").build());
		
		// 1:n
		List<Phone> phones1 = Arrays.asList
		(
			Phone.builder().token("QOWHSMFM291827").number("01012345678").build(),
			Phone.builder().token("IWQORR12WWQ").number("01056781290").build()
		);
		
		user1.setPhones(phones1);
		
		user2.bind(Phone.builder().token("ASDJW27461SHQP").number("01024798123").build());
		
		Post post1 = Post.builder().title("안녕하세요~").content("첫 글 올립니다.").build();
		user1.bind(post1);
		
		Post post2 = Post.builder().title("하이염").content("방가").build();
		user2.bind(post2);
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		Tag tag1 = Tag.builder().title("환상적").build();
		Tag tag2 = Tag.builder().title("야호").build();
		
		PostTag postTag1 = PostTag.builder().tag(tag1).build();
		PostTag postTag2 = PostTag.builder().tag(tag2).build();
		
		PostTag postTag3 = PostTag.builder().tag(tag2).build();
		
		post1.bind(postTag1);
		post1.bind(postTag2);
		
		post2.bind(postTag3);
		
		post1.bind(Comment.builder().content("첫 댓글 달아요~").build());
		post1.bind(Comment.builder().content("두번째 댓글 달아요~").build());
		
		userRepository.saveAll(users);
		
		System.out.println("** Init Data End **");
	}
}
