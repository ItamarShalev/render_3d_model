from enum import Enum
import os
from lxml import etree
import xml.etree.ElementTree as ET
from tabulate import tabulate

class Color(Enum):
    NO_COLOR = "0m"
    BLACK = "0;30m"
    DARK = "0;31m"
    GRAY = "0;32m"
    LIGHT_RED = "1;31m"
    RED = "0;31m"
    BLUE = "0;34m"
    YELLOW = "1;33m"
    PURPLE = "1;35m"
    GREEN = "1;32m"

    @staticmethod
    def color_text(text, color):
        if text is None:
            return None
        return "\033[" + str(color.value) + text + "\033[" + str(Color.NO_COLOR.value)


    @staticmethod
    def color_list(ls, color):
        if ls is None:
            return None
        for i in range(len(ls)):
            ls[i] = Color.color_text(ls[i],color)
        return ls



class ErrorElement():
    def __init__(self, attrib):
        self.source = attrib["source"]
        self.source =  self.source[self.source.rfind(".") + 1:]
        self.line = attrib["line"]
        self.severity = attrib["severity"].capitalize()
        self.message = attrib["message"]
        if "column" in attrib.keys():
            self.column = attrib["column"]
        else:
            self.column = None

    def is_error(self):
        return self.severity == "Error"

    def get_string(self):
        data = ""
        data += f"Line: {self.line}\t"
        if self.column is not None:
            data += f"Column: {self.column}\t"
        data += f"Severity: {self.severity}\t"
        data += f"Message: {self.message}\t"
        data += f"Source: {self.source}\t"
        return data

    def to_list(self, color = None):
        ls = [self.line, self.column, self.severity, self.message, self.source]
        if color is None:
            return ls
        else:
            return Color.color_list(ls, color)

    @staticmethod
    def headers(color = None):
        ls = ["Line", "Column", "Severity", "Message", "Source"]
        if color is None:
            return ls
        else:
            return Color.color_list(ls, color)


def parse_checkstyle_xml(file_name):
    try:
        tree = ET.parse(file_name)
    except Exception as e:
        print(Color.color_text(e.__str__(), Color.LIGHT_RED))
        exit(1)
        return
    root = tree.getroot()
    files = {}
    there_was_an_error = False
    name = ""
    for file in root:
        location = file.attrib["name"]
        files[location] = []
        for error in file:
            error_element = ErrorElement(error.attrib)
            there_was_an_error = there_was_an_error or error_element.is_error()
            files[location].append(error_element)
    return files, there_was_an_error


def print_errors(files_arrays):
    print_something = False
    if files_arrays is None or len(files_arrays) == 0:
        return print_something
    for location_file, errors_array in files_arrays.items():
        if len(errors_array) == 0:
            continue
        print(Color.color_text(f"File: {location_file}", Color.LIGHT_RED))
        ls = []
        for error_object in errors_array:
            ls.append(error_object.to_list(Color.LIGHT_RED if error_object.is_error() else Color.YELLOW))
        print_something = True
        table = tabulate(ls ,headers=ErrorElement.headers(Color.PURPLE), showindex=True, tablefmt="pretty",
                         stralign="left")
        print(table)
    return print_something

def create_checkstyle_files():
    os.system("mvn clean -q")
    os.system("mvn checkstyle:checkstyle -q")

def main():
    create_checkstyle_files()
    files_arrays, there_was_error = parse_checkstyle_xml("target/checkstyle-result.xml")
    print_something = print_errors(files_arrays)
    if there_was_error:
        print(Color.color_text(f"Failed CheckStyle Code, Fix and try again.", Color.LIGHT_RED))
        exit(1)
    if print_something:
        print(Color.color_text(f"Success CheckStyle Code, Check the warnings !", Color.YELLOW))
    else:
        print(Color.color_text(f"Success CheckStyle Code, Perfect !", Color.GREEN))

if __name__ == '__main__':
    main()