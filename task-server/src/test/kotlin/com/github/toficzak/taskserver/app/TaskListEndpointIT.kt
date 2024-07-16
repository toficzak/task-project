//package com.github.toficzak.taskserver.app
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.github.toficzak.taskserver.app.dto.CommentDto
//import com.github.toficzak.taskserver.app.dto.TaskCreateDto
//import com.github.toficzak.taskserver.app.dto.TaskListCreateDto
//import com.github.toficzak.taskserver.persistence.MongoTask
//import com.github.toficzak.taskserver.persistence.MongoTaskList
//import org.hamcrest.CoreMatchers
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Disabled
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.data.mongodb.core.query.Query
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//import org.testcontainers.containers.MongoDBContainer
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//import org.testcontainers.utility.DockerImageName
//import java.util.*
//
//@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
//class TaskListEndpointIT {
//    // todo: add given/when/then
//    // todo: split endpoints to some more proper sub-domains
//
//    @Autowired
//    private val mongoTemplate: MongoTemplate? = null
//
//    @Autowired
//    private val mockMvc: MockMvc? = null
//
//    companion object {
//        @Container
//        @ServiceConnection
//        @JvmStatic
//        val mongoDbContainer = MongoDBContainer(DockerImageName.parse("mongo:latest")) // todo: keep same container name
//    }
//
//    @BeforeEach
//    fun init() {
//        mongoTemplate!!.findAllAndRemove(Query(), MongoTask::class.java)
//        mongoTemplate.findAllAndRemove(Query(), MongoTaskList::class.java)
//    }
//
//    @Test
//    fun shouldCreateNewTaskList() {
//        val dto = TaskListCreateDto(name = "test_list")
//
//        this.mockMvc!!.perform(
//            post("/lists")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsBytes(dto))
//                .accept(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk())
//            .andExpect(content().string(CoreMatchers.containsString("")))
//
//        val mongoTaskList = mongoTemplate!!.findAll(MongoTaskList::class.java)
//        assert(mongoTaskList.size == 1)
//        val taskList = mongoTaskList.first()
//        assert(taskList.name == dto.name)
//    }
//
//    @Test
//    fun shouldCreateNewTask() {
//        val dto = TaskCreateDto(
//            name = "test_name",
//            description = "description"
//        )
//
//        val mongoTaskList = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "task_list_name"
//        )
//
//        mongoTemplate!!.save(mongoTaskList)
//
//        this.mockMvc!!.perform(
//            post("/lists/${mongoTaskList.id}/tasks")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsBytes(dto))
//                .accept(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk())
//            .andExpect(content().string(CoreMatchers.containsString("")))
//
//        val tasks = mongoTemplate.findAll(MongoTask::class.java)
//        assert(tasks.size == 1)
//        val task = tasks.first()
//        assert(task.name == dto.name)
//    }
//
//    @Test
//    fun shouldMoveTaskBetweenLists() {
//        val task = MongoTask(
//            id = UUID.randomUUID().toString(),
//            name = "test_name",
//            description = "description"
//        )
//
//        mongoTemplate!!.save(task)
//
//        val mongoTaskList1 = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "source_task_list",
//            taskIds = mutableListOf(task.id)
//        )
//
//        val mongoTaskList2 = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "destination_task_list"
//        )
//
//        mongoTemplate.save(mongoTaskList1)
//        mongoTemplate.save(mongoTaskList2)
//
//        this.mockMvc!!.perform(
//            post("/tasks/${task.id}/list/${mongoTaskList2.id}")
//                .accept(MediaType.APPLICATION_JSON)
//        )
//
//        val resultLists = mongoTemplate.findAll(MongoTaskList::class.java)
//        val resultSourceList = resultLists.first { it.id == mongoTaskList1.id }
//        assert(resultSourceList.taskIds.isEmpty())
//        val resultDestinationList = resultLists.first { it.id == mongoTaskList2.id }
//        assert(resultDestinationList.taskIds.size == 1)
//        assert(resultDestinationList.taskIds.first() == task.id)
//    }
//
//    @Test
//    @Disabled("One fine day...")
//    fun noSuchTaskToMove() {
//        val mongoTaskList1 = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "source_task_list",
//            taskIds = mutableListOf()
//        )
//
//        mongoTemplate!!.save(mongoTaskList1)
//
//        this.mockMvc!!.perform(
//            post("/tasks/unknown_task/list/any_list")
//                .accept(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isNotFound())
//            .andReturn()
//    }
//
//    @Test
//    fun addComment() {
//        val task = MongoTask(
//            id = UUID.randomUUID().toString(),
//            name = "test_name",
//            description = "description"
//        )
//
//        mongoTemplate!!.save(task)
//
//        val commentDto = CommentDto(
//            content = "comment content"
//        )
//
//        this.mockMvc!!.perform(
//            post("/tasks/${task.id}/comments")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsBytes(commentDto))
//                .accept(MediaType.APPLICATION_JSON)
//        )
//
//        val resultTasks = mongoTemplate.findAll(MongoTask::class.java)
//        val resultTask = resultTasks.first { it.id == task.id }
//        assert(resultTask.comments.size == 1)
//        assert(resultTask.comments.first().content == commentDto.content)
//    }
//
//    @Test
//    fun updateComment() {
//        val task = MongoTask(
//            id = UUID.randomUUID().toString(),
//            name = "test_name",
//            description = "description",
//            comments = mutableListOf(
//                MongoTask.MongoComment(
//                    id = UUID.randomUUID().toString(),
//                    content = "old_value"
//                )
//            )
//        )
//
//        mongoTemplate!!.save(task)
//
//        val commentDto = CommentDto(
//            id = task.comments.first().id.toString(),
//            content = "new value"
//        )
//
//        this.mockMvc!!.perform(
//            put("/tasks/${task.id}/comments")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsBytes(commentDto))
//                .accept(MediaType.APPLICATION_JSON)
//        )
//
//        val resultTasks = mongoTemplate.findAll(MongoTask::class.java)
//        val resultTask = resultTasks.first { it.id == task.id }
//        assert(resultTask.comments.size == 1)
//        assert(resultTask.comments.first().content == "new value")
//    }
//
//    @Test
//    fun removeComment() {
//        val task = MongoTask(
//            id = UUID.randomUUID().toString(),
//            name = "test_name",
//            description = "description",
//            comments = mutableListOf(
//                MongoTask.MongoComment(
//                    id = UUID.randomUUID().toString(),
//                    content = "old_value"
//                )
//            )
//        )
//
//        mongoTemplate!!.save(task)
//
//        this.mockMvc!!.perform(
//            delete("/tasks/${task.id}/comments/${task.comments.first().id}")
//                .accept(MediaType.APPLICATION_JSON)
//        )
//
//        val resultTasks = mongoTemplate.findAll(MongoTask::class.java)
//        val resultTask = resultTasks.first { it.id == task.id }
//        assert(resultTask.comments.isEmpty())
//    }
//
//    @Test
//    fun shouldListTaskLists() {
//        val mongoTaskList = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "task_list_name"
//        )
//        val mongoTaskList2 = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "task_list_name2"
//        )
//
//        mongoTemplate!!.save(mongoTaskList)
//        mongoTemplate.save(mongoTaskList2)
//
//        val response = this.mockMvc!!.perform(
//            get("/lists")
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk())
//            .andReturn()
//
//        assert(response.response.contentAsString == "[{\"id\":\"123\",\"name\":\"task_list_name\"},{\"id\":\"124\",\"name\":\"task_list_name2\"}]")
//    }
//
//    @Test
//    fun shouldListTasks() {
//
//
//        val mongoTask1 = MongoTask(
//            id = UUID.randomUUID().toString(),
//            name = "task1",
//            description = "desc1",
//            comments = mutableListOf(
//                MongoTask.MongoComment(
//                    id = UUID.randomUUID().toString(),
//                    content = "content1"
//                )
//            )
//        )
//
//        val mongoTask2 = MongoTask(
//            id = UUID.randomUUID().toString(),
//            name = "task2",
//            description = "desc2",
//            comments = mutableListOf(
//                MongoTask.MongoComment(
//                    id = UUID.randomUUID().toString(),
//                    content = "content2"
//                )
//            )
//        )
//
//
//        mongoTemplate!!.save(mongoTask1)
//        mongoTemplate.save(mongoTask2)
//
//        val mongoTaskList = MongoTaskList(
//            id = UUID.randomUUID().toString(),
//            name = "task_list_name",
//            taskIds = mutableListOf(
//                mongoTask1.id,
//                mongoTask2.id
//            )
//        )
//        mongoTemplate.save(mongoTaskList)
//
//        val response = this.mockMvc!!.perform(
//            get("/lists/${mongoTaskList.id}/tasks")
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk())
//            .andReturn()
//
//        assert(response.response.contentAsString == "[{\"id\":\"${mongoTask1.id}\",\"name\":\"${mongoTask1.name}\"},{\"id\":\"${mongoTask2.id}\",\"name\":\"${mongoTask2.name}\"}]")
//
//    }
//}