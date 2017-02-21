package dao;

import entities.Post;

public interface IPostDAO {
	
	Post createPost(Post post);
	Post getPost(Integer id);
	Post updatePost(Post post);
	boolean removePost(Integer id);

}
