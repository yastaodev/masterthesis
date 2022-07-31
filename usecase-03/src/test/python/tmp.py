import polyglot

class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    @staticmethod
    @polyglot.export_value
    def build():
        return Person("Yasser", "Taoufiq")

    @polyglot.export_value
    def myfunc(self):
        print("Hello my name is " + self.name)