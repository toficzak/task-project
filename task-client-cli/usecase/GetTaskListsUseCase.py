from cache_wrapper.Data import Data
from http_wrapper.Requester import Requester
from usecase.cacher.TaskListsCacher import TaskListsCacher


class GetTaskListsUseCase:

    def __init__(self, requester: Requester):
        self.requester = requester

    def show_task_lists(self):
        print(f"Showing task lists")

        response = self.requester.get(
            endpoint=f"/lists"
        )

        datas = []
        counter = 1

        for item in response.content:
            list_id = str(item["id"])
            list_name = str(item["name"])
            data = Data(str(counter), list_id, list_name)
            counter += 1
            datas.append(data)

        TaskListsCacher().cacher.clear_file_then_persist_all(datas)

        for data in datas:
            print(data)


if __name__ == "__main__":
    GetTaskListsUseCase(Requester("http://localhost:8080")).show_task_lists()
