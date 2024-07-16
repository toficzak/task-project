from cache_wrapper.Data import Data
from http_wrapper.Requester import Requester
from usecase.GetContextTaskListUseCase import GetContextTaskListUseCase
from usecase.cacher.TaskListsCacher import TaskListsCacher
from usecase.cacher.TasksCacher import TasksCacher


class GetTasksUseCase:

    def __init__(self, args, requester):
        self.args = args
        self.requester = requester

    def get_tasks(self):
        alias_task_list_id = self.args.task_list_id
        task_list_id = None
        if alias_task_list_id is not None:
            task_list_id = TaskListsCacher().get_cacher().find_by_key(alias_task_list_id).value
        else:
            task_list_data = GetContextTaskListUseCase().get_context_task_list()
            if task_list_data is None:
                print("Error: no task list id provided, no context task list id")
                return
            task_list_id = task_list_data.key

        print(f"Showing tasks from : {task_list_id}")
        response = self.requester.get(endpoint=f"/lists/{task_list_id}/tasks")
        print(response.content)

        datas = []
        counter = 1

        for item in response.content:
            task_id = str(item["id"])
            task_name = str(item["name"])
            data = Data(str(counter), task_id, task_name)
            counter += 1
            datas.append(data)

        TasksCacher().cacher.clear_file_then_persist_all(datas)

        for data in datas:
            print(data)

        return datas


if __name__ == "__main__":
    class Args:
        def __init__(self, task_list_id):
            self.task_list_id = task_list_id


    GetTasksUseCase(Args(1), Requester("http://localhost:8082")).get_tasks()
