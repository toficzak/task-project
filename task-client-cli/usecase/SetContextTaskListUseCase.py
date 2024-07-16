from cache_wrapper.Data import Data
from usecase.cacher.ContextTaskListCacher import ContextTaskListCacher
from usecase.cacher.TaskListsCacher import TaskListsCacher


class SetContextTaskListUseCase:

    def __init__(self, args):
        self.args = args

    def set_context_task_list(self):
        """
        Get id of cached task list.
        Check cached task lists to find one with such id.
        If no cached task lists file, return.
        If found cached task list, save its id, name to context task list file
        """
        task_list_id = self.args.alias_task_list_id  # todo: validate it
        cached_task_list = TaskListsCacher().get_cacher().find_by_key(task_list_id)

        if cached_task_list is None:
            print(f"No task list associated with id: {task_list_id}")

        ContextTaskListCacher().cacher.clear_file_then_persist(Data(
            key=cached_task_list.value,
            name=cached_task_list.name)
        )


if __name__ == "__main__":
    class Args:
        def __init__(self, alias_task_list_id):
            self.alias_task_list_id = alias_task_list_id


    SetContextTaskListUseCase(Args(1)).set_context_task_list()
