#!/bin/python
#-!- encoding:utf8 -!-

import os

# ---------------------------------------- CONFIGURATION --------------------------------- #

SRC_ROOT = "../livraison/src/main"

# ------------------------------------------ FUNCTIONS ----------------------------------- #

# GLOBALS
TODO_LIST = [];
NOTES_LIST = [];

##
#   Extract notes, removeMeLater and todos
##
def extractUsefullData(filename, f):
    global TODO_LIST;
    global NOTES_LIST;
    i = -1;
    for line in f:
        i += 1;
        if "\\todo" in line:
            TODO_LIST.append("[%s:%s] - %s" % (filename, i, line.split("\\todo")[1]));
        elif "removeMeLater" in line:
            NOTES_LIST.append("[%s:%s] - %s" % (filename, i, line.split("removeMeLater")[1]));

##
#   Prints todo list and notes list
##
def printUsefullData():
    # Print all todos
    print("TODO LIST :\n");
    for todo in TODO_LIST:
        print(todo);
    # Print all notes
    print("NOTES LIST :\n");
    for note in NOTES_LIST:
        print(note);

##
#   Walk recursively in source directory
##
def recursiveScan():
    for root, dirs, files in os.walk(SRC_ROOT):
        path = root.split('/');
        print (len(path) - 1) *'---' , os.path.basename(root)       
        for filename in files:
            if ".java" in filename:
                with open(root+"/"+filename, 'r') as f:
                    extractUsefullData(filename, f);   

def patchJavaDocAuthor():
    print("Not implemented yet...");


# ------------------------------------------- SCRIPT ------------------------------------ #

recursiveScan();
printUsefullData();