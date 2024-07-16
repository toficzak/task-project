from http_wrapper.Requester import Requester
from usecase.GetContextTaskListUseCase import GetContextTaskListUseCase
from usecase.cacher.TaskListsCacher import TaskListsCacher


class AddTaskUseCase:

    def __init__(self, args, requester: Requester):
        self.args = args
        self.requester = requester

    def add_task(self):
        task_list_id = self.args.task_list_id
        task_list_data = None
        if task_list_id is not None:
            task_list_data = TaskListsCacher().get_cacher().find_by_key(task_list_id)
        else:
            task_list_data = GetContextTaskListUseCase().get_context_task_list()
            if task_list_data is None:
                print("Error: no task list id provided, no context task list id")
                return

        if task_list_data is None:
            raise Exception(f"No task list by id: {task_list_id}")

        print(f"Task list data: {task_list_data}")
        print(f"Value: {task_list_data.key}")

        json_data = {
            "name": f"{self.args.task_name}"
        }

        self.requester.post(
            endpoint=f"/lists/{task_list_data.key}/tasks",
            body=json_data
        )

        print(f"Added task {self.args.task_name} to list: {task_list_data.name}, {task_list_data.key}")


if __name__ == "__main__":
    class Args:
        def __init__(self, task_list_id):
            self.task_list_id = task_list_id
            self.task_name = "test_task_name"


    AddTaskUseCase(Args(1), Requester("http://localhost:8080")).add_task()
