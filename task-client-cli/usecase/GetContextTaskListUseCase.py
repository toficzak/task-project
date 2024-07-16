from cache_wrapper.Data import Data
from usecase.cacher.ContextTaskListCacher import ContextTaskListCacher


class GetContextTaskListUseCase:
    def get_context_task_list(self) -> Data:
        data = ContextTaskListCacher().get_cacher().get_line()
        print(f"Context task list id:{data.key}")  # todo: using name might be more readable
        return data


if __name__ == "__main__":
    GetContextTaskListUseCase().get_context_task_list()
