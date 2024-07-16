from http_wrapper.Requester import Requester
from usecase.cacher.TaskListsCacher import TaskListsCacher
from usecase.cacher.TasksCacher import TasksCacher


class MoveTaskUseCase:

    def __init__(self, args, requester: Requester):
        self.args = args
        self.requester = requester

    def move_task(self):
        print(f"Move task {self.args.alias_task_id} to list : {self.args.alias_task_list_id}")

        cached_task_id = TasksCacher().get_cacher().find_by_key(self.args.alias_task_id).value
        cached_task_list_id = TaskListsCacher().get_cacher().find_by_key(self.args.alias_task_list_id).value

        return self.requester.post(
            endpoint=f"/tasks/{cached_task_id}/list/{cached_task_list_id}"
        )


if __name__ == "__main__":
    class Args:
        def __init__(self, alias_task_id, alias_task_list_id):
            self.alias_task_id = alias_task_id
            self.alias_task_list_id = alias_task_list_id


    MoveTaskUseCase(Args(
        alias_task_id=1,
        alias_task_list_id=1), Requester("http://localhost:8080")
    ).move_task()
