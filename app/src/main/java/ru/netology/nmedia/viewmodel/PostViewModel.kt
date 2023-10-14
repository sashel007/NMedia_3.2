package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {

    private val empty = Post(
        id = 0L,
        content = "",
        author = "",
        likedByMe = false,
        likes = 0,
        published = "",
        sharings = 0,
        video = null
    )
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    val edited = MutableLiveData(empty)

    fun like(id: Long) = repository.like(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun resetEditingState() {
        edited.value = empty
    }

    fun addNewPost(content: String) {
        val newPost = empty.copy(content = content.trim(), id = 0L)
        repository.save(newPost)
    }

    fun updatePost(postId: Long, content: String) {
        // Получаем текущий пост
        val currentPost = repository.getById(postId) ?: return

        // Обновляем его
        val updatedPost = currentPost.copy(content = content.trim())

        // Сохраняем его
        repository.save(updatedPost)

        resetEditingState()
    }
}