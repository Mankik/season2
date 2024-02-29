from rest_framework import serializers
from .models import *


def createSerializer(model_class, name_suffix='Serializer'):
    class Meta:
        model = model_class
        fields = '__all__'

    serializer_name = f"{model_class.__name__}{name_suffix}"
    serializer_class = type(serializer_name, (serializers.ModelSerializer,), {'Meta': Meta})

    return serializer_class
